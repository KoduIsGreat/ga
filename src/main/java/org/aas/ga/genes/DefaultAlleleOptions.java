package org.aas.ga.genes;

import java.util.*;

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

    private LinkedHashSet options = new LinkedHashSet<>();
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

    @Override
    public void buildRandomOptions(int n) {
        throw new UnsupportedOperationException("buildRandomOptions is not available for default Alleleoptions") ;
    }

    @Override
    public Object pickRandom()
    {
        Object item;
        Integer randm = new Random().nextInt(options.size());
        int i = 0;
        Iterator itr = options.iterator();
        do
        {
            item = itr.next();
            i++;
        }
        while(i<randm);
        return item;
    }
}
