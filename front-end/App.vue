<template>
  <div class="main">
    <div class="tools">
      <ul class="shapes">
        <li><button @click="usePencilTool"><span>&#9998;</span> Pencil</button></li>
        <li><button @click="drawLine"><span style="font-size: medium;">&#9644; </span> Line</button></li>
        <li> <button @click="drawCircle"> <span>&#9679; </span> Circle</button></li>
        <li><button @click="drawRectangle"><span>&#9644; </span> Rectangle</button></li>
        <li> <button @click="drawSquare"><span> &#9632; </span> Square</button></li>
        <li><button @click="drawTriangle"><span style="font-size: smaller;">&#9650; </span>Triangle</button></li>
          <li><button class="elip" @click="drawEllipse"><svg height="20" width="30"><ellipse cx="12" cy="10" rx="10" ry="5"
              style="display: inline-block; fill:black;" /></svg><span style="font-size: large">Ellipse</span></button></li>

      </ul><br>
      <div class="color-picker" @click="changeFillColor($event)">Fill Color 
    <div class="gradient"></div>
    </div><br>
    <div class="color-picker" @click="changeStrokeColor($event)">Stroke Color 
    <div class="gradient"></div>
    </div><br>
    <div class="size-picker" @click="changeStrokewidth($event)">
  <label style="font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif; font-weight: bold;" for="shapeSize">Stroke Size</label><br>
  <input type="range" id="shapeSize" min="1" max="10" v-model="shapeSize" />
</div>

    </div>
    
    
    <div class="board" ref="stageContainer">
      
    </div>
  </div>
</template>

<script>
import Konva from 'konva';
import ShapeFactory from './ShapeFactory';

export default {
  data(){
    return {
      fill_color : 'aliceblue',
      stroke_color : 'black',
      stroke_Width :2,
    }
  },
  mounted() {
    this.stage = new Konva.Stage({
      container: this.$refs.stageContainer,
      width: window.innerWidth,
      height: window.innerHeight,
    });
    this.layer = new Konva.Layer();
    this.stage.add(this.layer);
  },

  methods: {
  usePencilTool() {
  this.stage.off('mousedown');
  this.stage.off('mousemove');
  this.stage.off('mouseup');
  let isDrawing = false;
  let lastLine;

  this.stage.on('mousedown touchstart', (e) => {
    isDrawing = true;
    let pos = this.stage.getPointerPosition();
    lastLine = new Konva.Line({
      stroke: this.fill_color,
      strokeWidth: this.stroke_Width,
      lineCap: 'round', // Smooth line ends
      lineJoin: 'round', // Smooth line corners
      points: [pos.x, pos.y],
    });
    this.layer.add(lastLine);
  });

  this.stage.on('mousemove touchmove', () => {
    if (!isDrawing) {
      return;
    }
    let pos = this.stage.getPointerPosition();
    let newPoints = lastLine.points().concat([pos.x, pos.y]);
    lastLine.points(newPoints);
    this.layer.batchDraw();
  });

  this.stage.on('mouseup touchend', () => {
    isDrawing = false;
  });
},
    drawLine() {
  this.stage.off('mousedown');
  this.stage.off('mousemove');
  this.stage.off('mouseup');

  let startx = 0;
  let starty = 0;
  let isDrawing = false;
  let line;

  this.stage.on('mousedown', (e) => {
    if (!isDrawing) {
      isDrawing = true;
      startx = e.evt.offsetX;
      starty = e.evt.offsetY;

      line = ShapeFactory.createShape('line', {
        points: [startx, starty],
        stroke: this.fill_color,
        strokeWidth: this.stroke_Width,
      });

      this.layer.add(line);
      this.stage.batchDraw();

      this.stage.on('mousemove', (e) => {
        if (isDrawing) {
          const endx = e.evt.offsetX;
          const endy = e.evt.offsetY;
          line.points([startx, starty, endx, endy]);
          this.stage.batchDraw();
        }
      });

      this.stage.on('mouseup', () => {
        if (isDrawing) {
          isDrawing = false;
          this.stage.off('mousemove');
          this.stage.off('mouseup');
        }
      });
    }
  });
},

    drawCircle() {
      this.stage.off('mousedown');
      this.stage.off('mousemove');
      this.stage.off('mouseup');
      let startx = 0;
      let starty = 0;
      let isDrawing = false;
      let circle;

  this.stage.on('mousedown', (e) => {
    if (!isDrawing) {
      isDrawing = true;
      startx = e.evt.offsetX;
      starty = e.evt.offsetY;
      
      circle = ShapeFactory.createShape('circle', {
        x: startx,
        y: starty,
        radius: 0,
        fill: this.fill_color,
        strokeWidth: this.stroke_Width,
        stroke : this.stroke_color,
      });
      
      this.layer.add(circle);
      this.stage.batchDraw();
      
      this.stage.on('mousemove', (e) => {
        if (isDrawing) {
          const radius = Math.sqrt(
            Math.pow(e.evt.offsetX - startx, 2) +
            Math.pow(e.evt.offsetY - starty, 2)
          );

          circle.radius(radius);
          this.stage.batchDraw();
        }
      });

      this.stage.on('mouseup', () => {
        if (isDrawing) {
          isDrawing = false;
          this.stage.off('mousemove');
          this.stage.off('click');
        }
      });
    }
  });
},
  drawRectangle() {
    this.stage.off('mousedown');
    this.stage.off('mousemove');
    this.stage.off('mouseup');
  let startx = 0;
  let starty = 0;
  let isDrawing = false;
  let rectangle;

  this.stage.on('mousedown', (e) => {
    if (!isDrawing) {
      isDrawing = true;
      startx = e.evt.offsetX;
      starty = e.evt.offsetY;
      
      rectangle = ShapeFactory.createShape('rectangle', {
        x: startx,
        y: starty,
        width: 0,
        height: 0,
        fill: this.fill_color,
        strokeWidth : this.stroke_Width,
        stroke :this.stroke_color,
      });
      this.stage.on('mousemove', (e) => {
        if (isDrawing) {
          const width = e.evt.offsetX - startx;
          const height = e.evt.offsetY - starty;
          rectangle.width(width);
          rectangle.height(height);
          this.stage.batchDraw();
        }
      });
      this.stage.on('mouseup', () => {
        if (isDrawing) {
          isDrawing = false;
          this.stage.off('mousemove');
          this.stage.off('click');
        }
      });
      
      this.layer.add(rectangle);
      this.stage.batchDraw();
    }
  });
},
drawSquare() {
  
  this.stage.off('mousedown');
  this.stage.off('mousemove');
  this.stage.off('mouseup');

  let startx = 0;
  let starty = 0;
  let isDrawing = false;
  let square;

  this.stage.on('mousedown', (e) => {
    if (!isDrawing) {
      isDrawing = true;
      startx = e.evt.offsetX;
      starty = e.evt.offsetY;

      square = ShapeFactory.createShape('rectangle', {
        x: startx,
        y: starty,
        width: 0,
        height: 0,
        fill: this.fill_color,
        strokeWidth : this.stroke_Width,
        stroke :this.stroke_color,
      });

      this.stage.on('mousemove', (e) => {
        if (isDrawing) {
          const width = e.evt.offsetX - startx;
          const height = e.evt.offsetY - starty;
          const side = Math.min(Math.abs(width), Math.abs(height));

         
          square.width(side * Math.sign(width));
          square.height(side * Math.sign(height));

          this.stage.batchDraw();
        }
      });

      this.stage.on('mouseup', () => {
        if (isDrawing) {
          isDrawing = false;
          this.stage.off('mousemove');
          this.stage.off('mouseup');
        }
      });

      this.layer.add(square);
      this.stage.batchDraw();
    }
  });
},

drawTriangle() {
  // Remove previous event listeners
  this.stage.off('mousedown');
  this.stage.off('mousemove');
  this.stage.off('mouseup');

  let startx = 0;
  let starty = 0;
  let isDrawing = false;
  let triangle;

  this.stage.on('mousedown', (e) => {
    if (!isDrawing) {
      isDrawing = true;
      startx = e.evt.offsetX;
      starty = e.evt.offsetY;

      triangle = new Konva.RegularPolygon({
        x: startx,
        y: starty,
        sides: 3,
        radius: 0,
        fill: this.fill_color,
        strokeWidth : this.stroke_Width,
        stroke :this.stroke_color,
      });

      this.layer.add(triangle);
      this.stage.batchDraw();

      this.stage.on('mousemove', (e) => {
        if (isDrawing) {
          const side = Math.sqrt(
            Math.pow(e.evt.offsetX - startx, 2) +
            Math.pow(e.evt.offsetY - starty, 2)
          );

          // Set the radius of the triangle to determine its size
          triangle.radius(side);
          this.stage.batchDraw();
        }
      });

      this.stage.on('mouseup', () => {
        if (isDrawing) {
          isDrawing = false;
          this.stage.off('mousemove');
          this.stage.off('mouseup');
        }
      });
    }
  });
},

    drawEllipse() {
      this.stage.off('mousedown');
      this.stage.off('mousemove');
      this.stage.off('mouseup');
  let startx = 0;
  let starty = 0;
  let isDrawing = false;
  let ellipse;

  this.stage.on('mousedown', (e) => {
    if (!isDrawing) {
      isDrawing = true;
      startx = e.evt.offsetX;
      starty = e.evt.offsetY;

      ellipse = ShapeFactory.createShape('ellipse', {
        x: startx,
        y: starty,
        radiusX: 0,
        radiusY: 0,
        fill: this.fill_color,
        strokeWidth : this.stroke_Width,
        stroke :this.stroke_color,
      });

      this.layer.add(ellipse);
      this.stage.batchDraw();

      this.stage.on('mousemove', (e) => {
        if (isDrawing) {
          const radiusX = Math.abs(e.evt.offsetX - startx);
          const radiusY = Math.abs(e.evt.offsetY - starty);

          ellipse.radiusX(radiusX);
          ellipse.radiusY(radiusY);
          this.stage.batchDraw();
        }
      });

      this.stage.on('mouseup', () => {
        if (isDrawing) {
          isDrawing = false;
          this.stage.off('mousemove');
          this.stage.off('click');
        }
      });
    }
  });
},
changeFillColor(event) {
    const gradient = document.querySelector('.gradient');
    const rect = gradient.getBoundingClientRect();
    const x = event.clientX - rect.left; 

    const colorStops = [
      '#000000', '#333333', '#666666', '#999999', '#CCCCCC', '#DDDDDD', '#EEEEEE', '#FFFFFF',
      '#FF0000', '#FF9900', '#FFFF00', '#00FF00', '#00FFFF', '#0000FF', '#9900FF', '#FF00FF',
      '#FF99CC', '#FFCC99', '#FFFF99', '#CCFFCC', '#99FFFF', '#CCFFFF', '#9999FF', '#FF99FF',
      '#FFCCCC', '#FFCC99', '#FFFFCC', '#CCFFCC', '#99FFCC', '#CCFFFF', '#99CCFF', '#FF99FF'
    ];
    const percentage = (x / rect.width) * 100;
    const colorIndex = Math.floor((percentage / 100) * (colorStops.length - 1));
      this.fill_color = colorStops[colorIndex];
 

  },
  changeStrokeColor(event) {
    const gradient = document.querySelector('.gradient');
    const rect = gradient.getBoundingClientRect();
    const x = event.clientX - rect.left; 

    const colorStops = [
      '#000000', '#333333', '#666666', '#999999', '#CCCCCC', '#DDDDDD', '#EEEEEE', '#FFFFFF',
      '#FF0000', '#FF9900', '#FFFF00', '#00FF00', '#00FFFF', '#0000FF', '#9900FF', '#FF00FF',
      '#FF99CC', '#FFCC99', '#FFFF99', '#CCFFCC', '#99FFFF', '#CCFFFF', '#9999FF', '#FF99FF',
      '#FFCCCC', '#FFCC99', '#FFFFCC', '#CCFFCC', '#99FFCC', '#CCFFFF', '#99CCFF', '#FF99FF'
    ];
    const percentage = (x / rect.width) * 100;
    const colorIndex = Math.floor((percentage / 100) * (colorStops.length - 1));
      this.stroke_color = colorStops[colorIndex];
 

  },
  changeStrokewidth(event){
    this.stroke_Width = event.target.value;
  }
  },
  

  
};
</script>

<style>
  body
  {
    background-color: rgb(230, 88, 164);
    margin: 0;
  }
  .main
  {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    height: 100vh;


  }
  .tools
  {
    width: 20%;
    background-color: aliceblue;
    margin: 50px 50px;
    border-radius: 10px;
    
  }
  .board
  {
    margin-top: 50px;
    margin-bottom: 50px;
    margin-right: 50px;
    width: 70%;
    background-color: aliceblue;
    border-radius: 10px;
    overflow: scroll;
  }
  .shapes
  {
    margin: 0px 0px;
    padding: 0px 0px;  
  }
  li
  {
    list-style: none;
  
  }
  button
  {
    box-shadow: none;
    outline: none;
    padding: 10px;
    background: none;
    border: none;
    font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
    font-size: larger;

  }
  li button:hover
  {
    color: #e658a4;
  }
  span
  {
    font-size: larger;    
  }
  .elip {
    display: flex;
  }
  svg :hover
  {
    fill: rgb(230, 88, 164);
  }
  .color-picker {
    font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
    font-weight: bold;
  width: 100%;
  position: relative;
  overflow: hidden;
  cursor: pointer;
}

.gradient {
  height: 20px;
  width: 100%;
  background: linear-gradient(to right, 
    #000000, #333333, #666666, #999999, #CCCCCC, #DDDDDD, #EEEEEE, #FFFFFF, 
    #FF0000, #FF9900, #FFFF00, #00FF00, #00FFFF, #0000FF, #9900FF, #FF00FF, 
    #FF99CC, #FFCC99, #FFFF99, #CCFFCC, #99FFFF, #CCFFFF, #9999FF, #FF99FF, 
    #FFCCCC, #FFCC99, #FFFFCC, #CCFFCC, #99FFCC, #CCFFFF, #99CCFF, #FF99FF
  );
}
.size-picker input{
  width: 95%;
  
}


  
   
</style>