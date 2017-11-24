package io.github.scarger.referme.interaction;

/**
 * Created by Synch on 2017-11-18.
 */
public enum  MenuType {
    REFERRALS("Referrals"),
    NON("****/\\NoN/\\****");

    private String shouldContain;

    MenuType(String shouldContain){
        this.shouldContain = shouldContain;
    }

    public String getShouldContain() {
        return shouldContain;
    }
}
