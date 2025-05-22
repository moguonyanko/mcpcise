package practice.mcpcise.mountain;

class NullMountainOperator extends MountainOperator {

    public NullMountainOperator() {
        super(0, null);
    }

    @Override
    public boolean compare(int mountainElevation) {
        return true;
    }
}
