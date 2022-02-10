var canvas = document.getElementById('canvas');
// block property
var block = canvas.getContext('2d');
const widthBlock = 50;
const heightBlock = 50;
var x = (canvas.width - widthBlock)/2; // coordinate x of block
var y = (canvas.height - heightBlock)/2; // coordinate y of block

// Draw
function drawBlock()
{
    block.beginPath();
    block.rect(x, y, widthBlock, heightBlock)
    block.fill();
    block.closePath();
} 


// Action (keyboard controll)
var rightPressed = false;
var leftPressed = false;
var upPressed = false;
var downPressed = false;
// function to active controll
document.addEventListener('keydown', keyRight, false); // 
document.addEventListener('keyup', keyLeft, false); // 

function keyRight(e) { // e (event) key (keyboard)
    if(e.key == "Right" || e.key == "ArrowRight")
    {
        rightPressed = true;
    }   
    else if(e.key == "Left" || e.key == "ArrowLeft") {
        leftPressed = true;
    }
    else if(e.key == "Up" || e.key == "ArrowUp") {
        upPressed = true;
    }
    else if(e.key == "Down" || e.key == "ArrowDown") {
        downPressed = true;
    }
}

function keyLeft(e) {
    if(e.key == "Right" || e.key == "ArrowRight") {
        rightPressed = false;
    }
    else if(e.key == "Left" || e.key == "ArrowLeft") {
        leftPressed = false;
    }
    else if(e.key == "Up" || e.key == "ArrowUp") {
        upPressed = false;
    }
    else if(e.key == "Down" || e.key == "ArrowDown") {
        downPressed = false;
    }
}

function MoveBlock() {
    block.clearRect(0, 0, canvas.width, canvas.height);
    drawBlock();
    if(rightPressed) {
        x += 5;
        if (x + widthBlock > canvas.width){
            x = canvas.width - widthBlock;
        }
        console.log("X right: " + x)
    }
    else if(leftPressed) {
        x -= 5;
        if (x < 0){
            x = 0;
        }
        console.log("X left: " + x)
    }
    else if(upPressed) {
        y -= 5;
        if (y < 0){
            y = 0;
        }
        console.log("Y up: " + y)
    }
    else if(downPressed) {
        y += 5;
        if (y + heightBlock > canvas.height){
            y = canvas.height - heightBlock;
        }
        console.log("Y down: "+ y)
    }
}
// Active
setInterval(MoveBlock, 0.1);




