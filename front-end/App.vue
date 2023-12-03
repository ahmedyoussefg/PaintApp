<template>
  <div class="main">
    <div class="tools">
      <ul class="shapes">
        <li> <button @click="drawCircle()"> <span>&#9679; </span> Circle</button></li>
        <li><button @click="drawRectangle()"><span>&#9644; </span> Rectangle</button></li>
        <li> <button @click="drawSquare()"><span> &#9632; </span> Square</button></li>
        <li><button @click="drawTriangle()"><span style="font-size: smaller;">&#9650; </span>Triangle</button></li>
          <li><button class="elip" @click="drawElipse()"><svg height="20" width="30"><ellipse cx="12" cy="10" rx="10" ry="5"
              style="display: inline-block; fill:black;" /></svg><span style="font-size: large">Ellipse</span></button></li>
      </ul>
    </div>
    
    <div class="board" ref="container">
      
    </div>
  </div>
</template>

<script>
import Konva from 'konva';

export default {
  mounted() {
    this.stage = new Konva.Stage({
      container: this.$refs.container,
      width: window.innerWidth,
      height: window.innerHeight,
    });

    this.layer = new Konva.Layer();
    this.stage.add(this.layer);
  },
  data() {
    return {
      drawingMode: null,
      drawingFunction: null,
      startX: 0,
      startY: 0,
      drawingShape: null,
    };
  },
  methods: {
    removeEventListeners() {
      if (this.drawingFunction) {
        this.stage.off('mousedown touchstart');
        this.stage.off('mousemove touchmove');
        this.stage.off('mouseup touchend');
      }
    },
    addDrawingListeners() {
      this.stage.on('mousedown touchstart', this.drawingFunction);
    },
    drawRectangle() {
      this.removeEventListeners();
      this.drawingMode = 'rectangle';
      this.drawingFunction = this.drawRectangleInternal.bind(this); 
      this.addDrawingListeners();
    },
    drawCircle() {
      this.removeEventListeners();
      this.drawingMode = 'circle';
      this.drawingFunction = this.drawCircleInternal.bind(this);
      this.addDrawingListeners();
    },
    drawElipse() {
      this.removeEventListeners();
      this.drawingMode = 'elipse';
      this.drawingFunction = this.drawEllipseInternal.bind(this);
      this.addDrawingListeners();
    },
    drawSquare() {
      this.removeEventListeners();
      this.drawingMode = 'square'
      this.drawingFunction = this.drawSquareInternal.bind(this);
      this.addDrawingListeners();
    },
    drawTriangle() {
      this.removeEventListeners();
      this.drawingMode = 'triangle';
      this.drawingFunction = this.drawTriangleInternal.bind(this);
      this.addDrawingListeners();
    },
    drawRectangleInternal(e) {
      const pos = this.stage.getPointerPosition();
      this.startX = pos.x;
      this.startY = pos.y;

      this.drawingShape = new Konva.Rect({
        x: this.startX,
        y: this.startY,
        width: 0,
        height: 0,
        stroke: 'black',
        strokeWidth: 2,
      });

      this.layer.add(this.drawingShape);
      this.layer.batchDraw();

      this.stage.on('mousemove touchmove', (e) => {
        const newPos = this.stage.getPointerPosition();
        const width = newPos.x - this.startX;
        const height = newPos.y - this.startY;

        this.drawingShape.width(width);
        this.drawingShape.height(height);
        this.layer.batchDraw();
      });

      this.stage.on('mouseup touchend', () => {
        this.stage.off('mousemove touchmove');
        this.stage.off('mouseup touchend');
      });   
    
    },
    drawSquareInternal(e) {
      const pos = this.stage.getPointerPosition();
      this.startX = pos.x;
      this.startY = pos.y;

      this.drawingShape = new Konva.Rect({
        x: this.startX,
        y: this.startY,
        width: 0,
        height: 0,
        stroke: 'black',
        strokeWidth: 2,
      });

      this.layer.add(this.drawingShape);
      this.layer.batchDraw();

      this.stage.on('mousemove touchmove', (e) => {
        const newPos = this.stage.getPointerPosition();
        const width = newPos.x - this.startX;
        const height = newPos.y - this.startY;
        const size = Math.min(Math.abs(width), Math.abs(height));

        const signX = width >= 0 ? 1 : -1;
        const signY = height >= 0 ? 1 : -1;

        this.drawingShape.width(size * signX);
        this.drawingShape.height(size * signY);
        this.layer.batchDraw();
      });

      this.stage.on('mouseup touchend', () => {
        this.stage.off('mousemove touchmove');
        this.stage.off('mouseup touchend');
      });
    },
    drawCircleInternal(e) {
 
      const pos = this.stage.getPointerPosition();
      this.startX = pos.x;
      this.startY = pos.y;

      this.drawingShape = new Konva.Circle({
        x: this.startX,
        y: this.startY,
        radius: 0,
        stroke: 'black',
        strokeWidth: 2,
      });

      this.layer.add(this.drawingShape);
      this.layer.batchDraw();

      this.stage.on('mousemove touchmove', (e) => {
        const newPos = this.stage.getPointerPosition();
        const radius = Math.sqrt(
          Math.pow(newPos.x - this.startX, 2) + Math.pow(newPos.y - this.startY, 2)
        );

        this.drawingShape.radius(radius);
        this.layer.batchDraw();
      });

      this.stage.on('mouseup touchend', () => {
        this.stage.off('mousemove touchmove');
        this.stage.off('mouseup touchend');
      });
    },
    drawTriangleInternal(e) {
      const pos = this.stage.getPointerPosition();
      this.startX = pos.x;
      this.startY = pos.y;

      const points = [this.startX, this.startY, this.startX, this.startY];

      this.drawingShape = new Konva.Line({
        points: points,
        stroke: 'black',
        strokeWidth: 2,
      });

      this.layer.add(this.drawingShape);
      this.layer.batchDraw();

      this.stage.on('mousemove touchmove', (e) => {
        const newPos = this.stage.getPointerPosition();
        points[2] = newPos.x;
        points[3] = newPos.y;
        points[4] = this.startX - (newPos.x - this.startX);
        this.drawingShape.points(points);
        this.layer.batchDraw();
      });

      this.stage.on('mouseup touchend', () => {
        this.stage.off('mousemove touchmove');
        this.stage.off('mouseup touchend');
      });
    },
  
    drawEllipseInternal(e) {
      const pos = this.stage.getPointerPosition();
      this.startX = pos.x;
      this.startY = pos.y;

      this.drawingShape = new Konva.Ellipse({
        x: this.startX,
        y: this.startY,
        radiusX: 0,
        radiusY: 0,
        stroke: 'black',
        strokeWidth: 2,
      });

      this.layer.add(this.drawingShape);
      this.layer.batchDraw();

      this.stage.on('mousemove touchmove', (e) => {
        const newPos = this.stage.getPointerPosition();
        const radiusX = Math.abs(newPos.x - this.startX);
        const radiusY = Math.abs(newPos.y - this.startY);

        this.drawingShape.radiusX(radiusX);
        this.drawingShape.radiusY(radiusY);
        this.layer.batchDraw();
      });

      this.stage.on('mouseup touchend', () => {
        this.stage.off('mousemove touchmove');
        this.stage.off('mouseup touchend');
      });
    },
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
    color: rgb(230, 88, 164);
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
  
 
</style>


