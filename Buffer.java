/**
 * Created by r3eckon on 8/24/2016.
 * An array of any primitive data type or even an object used as a representation of the of the possibilities created at each location by a set algorithm.
 * Can be used to create , represent and edit anything tile/pixel based : Noise Maps , Game Boards , Game of Life , Pictures , Map Generation , etc.
 *
 */
 
 
class Buffer{
 
 
    int x , y , width ,height;  //buffer settings
    DataPoint[][] buffer;       //data buffer
    Data data;                  //data input and output
 
    public Buffer(int w , int h , Data data)//Initialize the buffer
    {
        this.width = w;
        this.height = h;
        this.data = data;
        initBuffer();//Finish initialization with creation of the array
    }
 
    void initBuffer()
    {
        buffer = new DataPoint[width*2][height*2];//Create a square buffer that can fit around a middle point. 0 , 0 is the middle. bounds are +-width and +-height;
    }
 
    void fill(){
        for(int i = -width ; i < width ; i++){
            for(int j = -height ; j < height ; j++){
 
                int x = i+width;//Array indexes
                int y = j+height;
 
                buffer[x][y] = data.get(i+this.x,j+this.y);//Query data input with position as parameters. (adding buffer position to be able to move the buffer around the data)
            }
 
        }
 
    }
 
    void move(int x , int y){
        this.x = x;
        this.y =y;
 
    }
 
 
}
 
class Data{
 
//This class controls the creation and behavior of the data points.
 
    int width , height; //Data bounds , use this to return a default value when the buffer is outside of the data
 
    long seed;
    Random rand;
 
 
    public Data(long s){
        this.seed = s;
    }
 
    //This method creates or reads data then controls it depending on the location we are reading from.
    //THIS IS THE PROCEDURE IN PROCEDURAL GENERATION
    DataPoint get(int x , int y){
 
        //First make sure you are within the data
 
        if(x < -width || x > +width || y < -height || y > height) return new DataPoint(DataPoint.NOTHING , x , y);//Return a default value if it isnt
 
        //Then lets create some location dependant white noise , which has some distinct artifacts depending on how you add up seed and position.
 
        rand = new Random(seed*x + seed*y);
 
        int value = (rand.nextBoolean()) ? DataPoint.SOMETHING : DataPoint.SOMETHINGELSE;
 
        //Last chance to edit the data , lets create an empty square in the middle
 
        if(x > -5 || x < 5 || y >-5 || y < 5) value = DataPoint.NOTHING;
 
        return new DataPoint(value, x , y);
 
    }
 
}
 
 
class DataPoint{
 
//Basic class for an integer based data point , add things as you see fit. The important part is the value and to keep a reference to the position within the data.
 
    static final int NOTHING = 0;
    static final int SOMETHING = 1;
    static final int SOMETHINGELSE = 2;
 
    int value , x , y;
 
    String name;
    Color color;
 
    public DataPoint(int val , int x , int y){
        this.x = x;
        this.y =y;
        this.init(val);
    }
    void init(int val){
        this.value = val;
        switch(value)
        {
            case NOTHING:
                name = "Nothing";
                color = Color.Transparent;
                break;
            case SOMETHING:
                name = "Something";
                color = Color.White;
                break;
            case SOMETHINGELSE:
                name = "Something Else";
                color= Color.Black;
                break;
            default:
                name = "Something Else ELSE";
                color = Color.Red;
                break;
        }
    }
 
}