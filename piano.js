// Browser translation of Piano.java. Coordinates, labels and mappings follow the original.
const canvas = document.querySelector('#piano');
const g = canvas.getContext('2d');
const keys = ['q','2','w','3','e','4','r','t','6','y','7','u','i','9','o','0','p','-','[',']'];
const whole = ['q','w','e','r','t','y','u','i','o','p','[',']'];
const notes = ['F','G','A','B','C','D','E','F','G','A','B','C'];
const whiteSounds = [0,2,4,6,7,9,11,12,14,16,18,19];
const sharpX = [84,184,284,484,584,784,884,984,1184];
const sharpSounds = [1,3,5,8,10,13,15,17,20];
const sounds = Array.from({length:24}, (_,i) => {
  const audio = new Audio(`key${i + 1}.wav`);
  audio.preload = 'auto';
  return audio;
});
let on = false;
let record = [];
let playing = false;
const background = new Image();
function rect(color,x,y,w,h) { g.fillStyle = color; g.fillRect(x,y,w,h); }
function label(color,text,x,y) { g.fillStyle = color; g.fillText(text,x,y); }
function draw() {
  g.clearRect(0,0,1219,768);
  if (background.complete && background.naturalWidth) g.drawImage(background,0,0);
  g.font = '12px Arial, sans-serif';
  g.textBaseline = 'alphabetic';
  for (let i = 0; i < 12; i++) {
    rect('#fff',i*100+9,458,100,300);
    g.strokeStyle = '#000';
    g.lineWidth = 1;
    g.strokeRect(i*100+9.5,458.5,100,300);
    label('#000',notes[i],i*100+59,658);
    label('#000',`( ${whole[i]} )`,i*100+52,678);
  }
  const sharpLabels = ['2','3','4','6','7','9','10','11'];
  for (let i = 0; i < sharpX.length; i++) {
    rect('#000',sharpX[i],458,50,150);
    if (i < 8) label('#fff',`( ${sharpLabels[i]} )`,sharpX[i]+(i < 6 ? 15 : 13),500);
  }
  rect('#000',1284,458,50,150);
  rect('#000',1384,458,50,150);
  rect('#f00',50,50,200,100);
  label('#000','Record',130,100);
  rect('#0f0',300,50,200,100);
  label('#000','Play',390,100);
  label('#0f0','Instructions:',800,50);
  label('#0f0','You can click the keys to play or use the keys on the keyboard to play',700,70);
  label('#0f0','Click record to record the notes you play',700,90);
  label('#0f0','Click record again to stop recording',700,110);
  label('#0f0','Click play to play the notes you recorded only AFTER you have finished recording',700,130);
}
background.onload = draw;
background.onerror = () => alert('Trouble loading pictures.');
background.src = 'galaxy.png';
draw();
function playSound(i) {
  const audio = sounds[i];
  audio.currentTime = 0;
  audio.play().catch(error => console.error('Unable to play piano sample:',error));
}
function playNote(i) {
  playSound(i);
  // Retain the original 500-note capacity without its array overflow crash.
  if (on && record.length < 500) record.push(i);
}
async function playback() {
  playing = true;
  try {
    for (const i of record) {
      playSound(i);
      await new Promise(resolve => setTimeout(resolve,300));
    }
  } finally { playing = false; }
}
function mouseDown(x,y) {
  if (playing) return;
  if (x >= 50 && x <= 250 && y >= 50 && y <= 150) {
    if (!on) record = [];
    on = !on;
  }
  if (x >= 300 && x <= 500 && y >= 50 && y <= 150 && !on) {
    playback();
  }
  for (let i = 0; i < sharpX.length; i++) {
    if (x >= sharpX[i] && x <= sharpX[i]+50 && y >= 458 && y <= 608) {
      playNote(sharpSounds[i]);
      return;
    }
  }
  // Inclusive boundaries intentionally match the original Java hit testing.
  for (let i = 0; i < 12; i++) {
    if (x >= i*100+9 && x <= (i+1)*100+9 && y >= 458 && y <= 768) playNote(whiteSounds[i]);
  }
}
canvas.addEventListener('pointerdown',event => {
  if (event.button !== 0) return;
  event.preventDefault();
  canvas.focus();
  const bounds = canvas.getBoundingClientRect();
  mouseDown((event.clientX-bounds.left)*1219/bounds.width,(event.clientY-bounds.top)*768/bounds.height);
});
document.addEventListener('keydown',event => {
  if (event.ctrlKey || event.metaKey || event.altKey) return;
  const i = keys.indexOf(event.key);
  if (i >= 0) {
    event.preventDefault();
    if (!playing) playNote(i);
  }
});
