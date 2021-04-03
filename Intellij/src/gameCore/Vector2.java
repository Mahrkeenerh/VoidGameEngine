package gameCore;

public class Vector2 {

    public double x, y;

    public Vector2() {

        this.x = 0;
        this.y = 0;
    }

    public Vector2(double x, double y) {

        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 v2) {

        this.x = v2.x;
        this.y = v2.y;
    }

    public static Vector2 Zero() {

        return new Vector2();
    }

    public static Vector2 Left() {

        return new Vector2(-1, 0);
    }

    public static Vector2 Right() {

        return new Vector2(1, 0);
    }

    public static Vector2 Up() {

        return new Vector2(0, 1);
    }

    public static Vector2 Down() {

        return new Vector2(0, -1);
    }
}
