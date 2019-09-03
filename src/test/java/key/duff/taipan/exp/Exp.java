package key.duff.taipan.exp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import utility.collections.CountingMap;

class Exp
{
    protected static Random rand;
    
    @BeforeAll
    public static void beforeAll()
    {
        rand = new Random();
    }
    
    @Disabled
    @ParameterizedTest
    @ValueSource ( ints = { 10, 11, 12, 13, 14, 15, 16 } )
    void test ( int base )
    {
        System.out.println ( "\nBase: " + base );
        
        for ( int priceScale = 1; priceScale <= 3; priceScale++ )
            for ( int extreme = 0; extreme < 9*4; extreme++ )
                for ( int high = 0; high < 2; high++ )
                {
                    int price = base / 2 * priceScale;
                    
                    if ( extreme > 0 )
                        System.out.println ( price );
                    else
                        if ( high == 0 )
                            System.out.println ( price / 5 );
                        else
                        {
                            for ( int highScale=5; highScale <= 9; highScale++ )
                                System.out.println ( price * highScale );
                        }
                }
    }
    
    @ParameterizedTest
    @ValueSource ( ints = { 10, 11, 12, 13, 14, 15, 16 } )
    void testFunction ( int base )
    {
        CountingMap<Integer> counts = new CountingMap<> ();
        
        prices ( base, counts );
        
        List<Integer> prices = new ArrayList<> ( counts.keySet () );
        Collections.sort ( prices );
        
        System.out.println ( "\nBase: " + base );
        
        for ( int price : prices )
            System.out.printf ( "%d\t%d\n", price, counts.get ( price ) );
    }
    
    @Test
    public void testOverall()
    {
        CountingMap<Integer> counts = new CountingMap<> ();

        for ( int base = 10; base <= 16; base++ )
            prices ( base, counts );

        System.out.println ( "\nOverall" );
        
        List<Integer> prices = new ArrayList<> ( counts.keySet () );
        Collections.sort ( prices );
        
        for ( int price : prices )
            System.out.printf ( "%d\t%d\n", price, counts.get ( price ) );
    }
    
    @Test
    public void testNormal()
    {
        CountingMap<Integer> counts = new CountingMap<> ();

        final double Mean = 13.4847;
        final double StdDev = 11.7953; 
                
        for ( int n=0; n < 700000; n++ )
        {
            int price = Double.valueOf ( rand.nextGaussian () * StdDev + Mean ).intValue ();
            counts.put ( price );
        }

        System.out.println ( "\nNormal" );
        
        List<Integer> prices = new ArrayList<> ( counts.keySet () );
        Collections.sort ( prices );
        
        for ( int price : prices )
            System.out.printf ( "%d\t%d\n", price, counts.get ( price ) );
    }
    
    private void prices ( int base, CountingMap<Integer> counts )
    {
        for ( int i=0; i < 100000; i++ )
        {
            int price = base / 2 * ( Math.abs ( rand.nextInt () ) % 3 + 1 );
            
            if ( Math.abs ( rand.nextInt () ) % 9 == 0 )
                if ( Math.abs ( rand.nextInt () ) % 4 == 0 )
                    if ( rand.nextBoolean () )
                        price /= 5;
                    else
                        price *= Math.abs ( rand.nextInt () ) % 5 + 5;
            
            counts.put ( price );
        }
        
    }
}
