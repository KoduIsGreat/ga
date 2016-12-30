package org.aas.ga.genes;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Adam on 8/28/2016.
 */
public enum DefaultAlleleOptions implements AlleleOptions
{
    ALL_ASCII(Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
            "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
            "0","1","2","3","4","5","6","7","8","9","0","!","@","#","$","%","^","&","*","(",")","-","=","_","+",".",
            ">",",","<","/","?",";",":","{","}"," ","`","~")),
    ALPHA_NUMERIC(Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
            "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
            "0","1","2","3","4","5","6","7","8","9","0")),
    ALL_ALPHA(Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
            "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z")),
    UPPER_ALPHA(Arrays.asList("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z")),
    LOWER_ALPHA(Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z")),
    BINARY(Arrays.asList(0,1)),
    BASE10(Arrays.asList(0,1,2,3,4,5,6,7,8,9)),
    DNA(Arrays.asList("A","C","T","G"));

    private Set options = new HashSet<>();
    DefaultAlleleOptions(){}
    DefaultAlleleOptions(Collection options){
        this.options.addAll(options);
    }

    @Override
    public Collection getOptions() {
        return options;
    }
    @Override
    public void setOptions(Collection options){
        this.options.addAll(options);
    }
}
