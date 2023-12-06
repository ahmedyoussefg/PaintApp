import Konva from 'konva';
 class Triangle{
    static drawTriangle(properties)
    {
        return new Konva.RegularPolygon({
            ...properties,
            sides: 3,
          });
    }
 }
 export default Triangle;