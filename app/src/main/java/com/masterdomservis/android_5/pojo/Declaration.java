package com.masterdomservis.android_5.pojo;
import android.os.Parcel;
import android.os.Parcelable;
public class Declaration implements Parcelable{

    private int DECLID;
    private int DECLN;
    private String DECLDATA;
    private int KVN;
    private String TELEFON;
    private String FIO;
    private String DECLARATION;
    private String DECLTIME;
    private int PODCOD;
    private int MASTERID;
    private String DATAVIPOLN;
    private int SOSTZAKAZID;
    private String NOTES;
    private String ZAPLANIR;
    private String USERNAME;

    protected Declaration(Parcel in) {
        DECLID = in.readInt();
        DECLN = in.readInt();
        DECLDATA = in.readString();
        KVN = in.readInt();
        TELEFON = in.readString();
        FIO = in.readString();
        DECLARATION = in.readString();
        DECLTIME = in.readString();
        PODCOD = in.readInt();
        MASTERID = in.readInt();
        DATAVIPOLN = in.readString();
        SOSTZAKAZID = in.readInt();
        NOTES = in.readString();
        ZAPLANIR = in.readString();
        USERNAME = in.readString();
    }

    @Override
    public String toString(){
        return String.format("Заявка №%s", DECLID);
    }

    public Declaration(int DECLID, int DECLN, String DECLDATA, int KVN, String TELEFON, String FIO, String DECLARATION, String DECLTIME, int PODCOD, int MASTERID, String DATAVIPOLN, int SOSTZAKAZID, String NOTES, String ZAPLANIR, String USERNAME) {
        this.DECLID = DECLID;
        this.DECLN = DECLN;
        this.DECLDATA = DECLDATA;
        this.KVN = KVN;
        this.TELEFON = TELEFON;
        this.FIO = FIO;
        this.DECLARATION = DECLARATION;
        this.DECLTIME = DECLTIME;
        this.PODCOD = PODCOD;
        this.MASTERID = MASTERID;
        this.DATAVIPOLN = DATAVIPOLN;
        this.SOSTZAKAZID = SOSTZAKAZID;
        this.NOTES = NOTES;
        this.ZAPLANIR = ZAPLANIR;
        this.USERNAME = USERNAME;
    }

    public int getDECLID() {
        return DECLID;
    }

    public void setDECLID(int DECLID) {
        this.DECLID = DECLID;
    }

    public int getDECLN() {
        return DECLN;
    }

    public void setDECLN(int DECLN) {
        this.DECLN = DECLN;
    }

    public String getDECLDATA() {
        return DECLDATA;
    }

    public void setDECLDATA(String DECLDATA) {
        this.DECLDATA = DECLDATA;
    }

    public int getKVN() {
        return KVN;
    }

    public void setKVN(int KVN) {
        this.KVN = KVN;
    }

    public String getTELEFON() {
        return TELEFON;
    }

    public void setTELEFON(String TELEFON) {
        this.TELEFON = TELEFON;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public String getDECLARATION() {
        return DECLARATION;
    }

    public void setDECLARATION(String DECLARATION) {
        this.DECLARATION = DECLARATION;
    }

    public String getDECLTIME() {
        return DECLTIME;
    }

    public void setDECLTIME(String DECLTIME) {
        this.DECLTIME = DECLTIME;
    }

    public int getPODCOD() {
        return PODCOD;
    }

    public void setPODCOD(int PODCOD) {
        this.PODCOD = PODCOD;
    }

    public int getMASTERID() {
        return MASTERID;
    }

    public void setMASTERID(int MASTERID) {
        this.MASTERID = MASTERID;
    }

    public String getDATAVIPOLN() {
        return DATAVIPOLN;
    }

    public void setDATAVIPOLN(String DATAVIPOLN) {
        this.DATAVIPOLN = DATAVIPOLN;
    }

    public int getSOSTZAKAZID() {
        return SOSTZAKAZID;
    }

    public void setSOSTZAKAZID(int SOSTZAKAZID) {
        this.SOSTZAKAZID = SOSTZAKAZID;
    }

    public String getNOTES() {
        return NOTES;
    }

    public void setNOTES(String NOTES) {
        this.NOTES = NOTES;
    }

    public String getZAPLANIR() {
        return ZAPLANIR;
    }

    public void setZAPLANIR(String ZAPLANIR) {
        this.ZAPLANIR = ZAPLANIR;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(DECLID);
        dest.writeInt(DECLN);
        dest.writeString(DECLDATA);
        dest.writeInt(KVN);
        dest.writeString(TELEFON);
        dest.writeString(FIO);
        dest.writeString(DECLARATION);
        dest.writeString(DECLTIME);
        dest.writeInt(PODCOD);
        dest.writeInt(MASTERID);
        dest.writeString(DATAVIPOLN);
        dest.writeInt(SOSTZAKAZID);
        dest.writeString(NOTES);
        dest.writeString(ZAPLANIR);
        dest.writeString(USERNAME);
    }

    public static final Parcelable.Creator<Declaration> CREATOR = new Parcelable.Creator<Declaration>() {
        // распаковываем объект из Parcel
        public Declaration createFromParcel(Parcel in) {
            return new Declaration(in);
        }

        @Override
        public Declaration[] newArray(int size) {
            return new Declaration[size];
        }
    };
}
