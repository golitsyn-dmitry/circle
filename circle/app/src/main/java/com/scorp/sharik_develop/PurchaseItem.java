package com.scorp.sharik_develop;

public class PurchaseItem {

    /** field for image of the sharik*/
    private int mImage;

    /** field for price of the sharik*/
    private int mPrice;

    /**
     *
     * @param mImage for image of the sharik
     * @param mPrice for price of the sharik
     */
    public PurchaseItem(int mImage,
                    int mPrice) {
        this.mImage = mImage;
        this.mPrice = mPrice;
    }

    /** get image of the sharik*/
    public int getmImage() {
        return mImage;
    }

    /** set image of the sharik*/
    public void setmImage(int mImage) {
        this.mImage = mImage;
    }

    /** get price of the sharik*/
    public int getmPrice() {
        return mPrice;
    }

    /** set price of the sharik*/
    public void setmPrice(int mPrice) {
        this.mPrice = mPrice;
    }

}
