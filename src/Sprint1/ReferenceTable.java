package Sprint1;


public class ReferenceTable {


    String name;
    String type;
    String address;
    String value;
    public ReferenceTable() {

    }

    public ReferenceTable(String name, String type, String address,String value) {
        this.name = name;
        this.type = type;
        this.address = address;
        this.value=value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getContent() {
        return value;
    }

    public void setContenido(String value) {
        this.value = value;
    }
    public String toString() {
        return "ReferenceTable [name=" + name + ", type=" + type + ", address=" + address + ", val: "+value+"]";
    }


}

