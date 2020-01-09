package yez.chicken.management;

// This is enum class for easy read ;)

public enum TableName
{
    PELANGGAN(1),
    ORDERS(2),
    DETAIL_ORDERS(3),
    PELAYAN(4),
    PRODUK(5);
    
    private final int value;
    
    // Enum constructor
    private TableName(int value){ this.value = value; }
    
    // Return enum value
    public int getValue(){ return value; }
}
