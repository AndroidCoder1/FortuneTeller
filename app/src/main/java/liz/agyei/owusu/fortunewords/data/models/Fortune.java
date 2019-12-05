
package liz.agyei.owusu.fortunewords.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import liz.agyei.owusu.fortunewords.utils.Configs;

public class Fortune {

    @SerializedName("fortune")
    private List<String> fortuneList;

    public void setFortuneList(List<String> fortuneList) {
        this.fortuneList = fortuneList;
    }

    @Override
    public String toString() {

        //Return default advice when the fortuneList is null

        StringBuilder sb = new StringBuilder();
        System.out.println(":::::"+fortuneList);
        if(fortuneList != null) {
            for (String item : fortuneList) {
                sb.append(item + "\n");
            }
        }else{
            sb.append(Configs.DEFAULT_ADVICE);
        }
        return sb.toString();
    }
}
