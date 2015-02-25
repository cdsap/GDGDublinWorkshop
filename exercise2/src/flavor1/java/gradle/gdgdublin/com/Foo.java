package gradle.gdgdublin.com;


public class Foo {
    int mValue;
    int factor = 1;

    public Foo(int mValue) {
        this.mValue = mValue;
    }

    public int getmValue() {
        return mValue * factor;
    }
}

