package games.rednblack.editor.renderer.components;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import games.rednblack.editor.renderer.data.ShapeVO;
import games.rednblack.editor.renderer.utils.PolygonUtils;

public class DimensionsComponent implements BaseComponent {
    public float width = 0;
    public float height = 0;

    public Rectangle boundBox;
    public Polygon polygon;

    public boolean hit(float x, float y) {
        if (polygon != null) {
            return polygon.contains(x, y);
        } else if (boundBox != null) {
            return (x >= boundBox.x && x < boundBox.x + boundBox.width && y >= boundBox.y && y < boundBox.y + boundBox.height);
        } else {
            return (x >= 0 && x < width && y >= 0 && y < height);
        }
    }

    public boolean isOverlap(Polygon target) {
        if (target == null) return false;
        if (polygon != null) {
            for (int i = 0; i < polygon.getVertices().length; i += 2) {
                if (target.contains(polygon.getVertices()[i], polygon.getVertices()[i + 1])) {
                    return true;
                }
            }
            for (int i = 0; i < target.getVertices().length; i += 2) {
                if (polygon.contains(target.getVertices()[i], target.getVertices()[i + 1])) {
                    return true;
                }
            }
        } else if (boundBox != null) {
            if (target.contains(boundBox.x, boundBox.y)) return true;
            if (target.contains(boundBox.x + boundBox.width, boundBox.y)) return true;
            if (target.contains(boundBox.x, boundBox.y + boundBox.height)) return true;
            if (target.contains(boundBox.x + boundBox.width, boundBox.y + boundBox.height))
                return true;
        } else {
            return target.contains(0, 0) || target.contains(width, 0) ||
                    target.contains(0, height) || target.contains(width, height);
        }
        return false;
    }

    public void setPolygon(PolygonComponent polygonComponent) {
        Vector2[] verticesArray = PolygonUtils.mergeTouchingPolygonsToOne(polygonComponent.vertices);
        float[] vertices = new float[verticesArray.length * 2];
        for (int i = 0; i < verticesArray.length; i++) {
            vertices[i * 2] = (verticesArray[i].x);
            vertices[i * 2 + 1] = (verticesArray[i].y);
        }
        polygon = new Polygon(vertices);
    }

    public void setFromShape(ShapeVO shape) {
        Vector2 minPoint = new Vector2();
        Vector2 maxPoint = new Vector2();
        if (shape.polygons != null) {
            for (int i = 0; i < shape.polygons.length; i++) {
                for (int j = 0; j < shape.polygons[i].length; j++) {
                    if (i == 0 && j == 0) {
                        minPoint.x = shape.polygons[i][j].x;
                        minPoint.y = shape.polygons[i][j].y;
                        maxPoint.x = shape.polygons[i][j].x;
                        maxPoint.y = shape.polygons[i][j].y;
                    }
                    if (minPoint.x > shape.polygons[i][j].x) minPoint.x = shape.polygons[i][j].x;
                    if (minPoint.y > shape.polygons[i][j].y) minPoint.y = shape.polygons[i][j].y;
                    if (maxPoint.x < shape.polygons[i][j].x) maxPoint.x = shape.polygons[i][j].x;
                    if (maxPoint.y < shape.polygons[i][j].y) maxPoint.y = shape.polygons[i][j].y;
                }
            }
            width = maxPoint.x - minPoint.x;
            height = maxPoint.y - minPoint.y;
        }
    }

    @Override
    public void reset() {
        width = 0;
        height = 0;
        boundBox = null;
        polygon = null;
    }
}
