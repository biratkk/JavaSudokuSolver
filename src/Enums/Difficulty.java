package Enums;

public enum Difficulty{
    EASY("easy"),MEDIUM("medium"),HARD("hard");
    private final String difficulty;

    Difficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String toString(){
        return difficulty;
    }
}