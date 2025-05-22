package practice.mcpcise.mountain;

interface MountainOperator {

    /**
     * 山の情報を操作するメソッド
     *
     * @param mountain 山の情報
     * @return true: 操作成功, false: 操作失敗
     */
    default boolean operate(Mountain mountain) {
        return true;
    }
}
