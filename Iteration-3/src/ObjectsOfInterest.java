package core;

public class ObjectsOfInterest {
    String name;
    String description;
    boolean owned;

    public ObjectsOfInterest(String name,String description,boolean owned) {
        this.name = name;
        this.description = description;
        this.owned = owned;
    }

    public String getDescription() {
        return name + " : " + description;
    }
}
