package com.samsungsds.msa.design.pattern;

class User {
    public static void main(String[] args) {
        System.out.println("220V");
        Adapter adapter = new AdapterImpl(new Adaptee());
        adapter.method("110V");
    }
}

interface Adapter {
    void method(String data);
}

class AdapterImpl implements Adapter {
    Adaptee adaptee;

    AdapterImpl(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    public void method(String data) {
        adaptee.adapteeMethod(data); // 위임
    }
}

class Adaptee {

    void adapteeMethod(String specialData) {
        System.out.println(specialData);
    }
}


