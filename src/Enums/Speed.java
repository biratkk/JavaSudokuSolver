package Enums;

public enum Speed {
    SLOW(16),
    MEDIUM(8),
    FAST(4),
    SUPER_FAST(2),
    EXTREME(1),
    INFINITE(0);

    private final int speed;

    Speed(int i) {
        this.speed = i;
    }
    public int toInt(){return speed;}
}
