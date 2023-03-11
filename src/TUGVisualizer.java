import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TUGVisualizer extends JFrame {
    DataGraphic graphic;
    Graphics graphics;
    public void setDataGraphic(DataGraphic graphic){
       this.graphic = graphic;
   }
    public TUGVisualizer(String TITLE, DataGraphic graphic) {
        int k = 0;
        setDataGraphic(graphic);
        setTitle(TITLE);
        setVisible(true);
        setSize(1920, 1080);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //drawGraphic(g,new GraphicColumn(new Rect(10,400),new Point(40,50)));
        graphic.drawDataGraphic(g);
        graphics = g;
    }
    public void refresh(){
        graphic.drawDataGraphic(graphics);
    }
    public void drawGraphic(Graphics g,GraphicColumn first_graphic_column){
        g.drawRect(first_graphic_column.point.x,first_graphic_column.point.y,first_graphic_column.rect.width,first_graphic_column.rect.height);
        g.drawRect(first_graphic_column.point.x,first_graphic_column.point.y+first_graphic_column.rect.height,first_graphic_column.rect.height,first_graphic_column.rect.width);
    }
    public static class Point{
        int get_value_Dw;
        int get_value_Up;

        public void setGet_value_Up(int get_value_Up) {
            this.get_value_Up = get_value_Up;
        }

        public void setGet_valueDW(int get_value) {
            this.get_value_Dw = get_value;
        }

        int x;
        int y;
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
    public static class Rect{
        int width;
        int height;

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public Rect(int width, int height){
            setHeight(height);
            setWidth(width);
        }

        @Override
        public String toString() {
            return "Rect{" +
                    "width=" + width +
                    ", height=" + height +
                    '}';
        }
    }
    public static class GraphicColumn{
        Rect rect;
        Point point;

        public Point getPoint() {
            return point;
        }

        public Rect getRect() {
            return rect;
        }

        public GraphicColumn(Rect rect, Point point){
        this.rect = rect;
        this.point = point;
        }

        @Override
        public String toString() {
            return "GraphicColumn{" +
                    "rect=" + rect +
                    ", point=" + point +
                    '}';
        }
    }
    public static class DataGraphic{
        public static int DW_HASH_INT = 0;
        public static int UP_HASH_INT = 1;
        HashMap<Integer,Point> dw_point_hash = new HashMap<>();
        HashMap<Integer,Point> up_point_hash = new HashMap<>();

        List<Point> oval_points = new ArrayList<>();

        public List<Point> getOval_points() {
            return oval_points;
        }

        public HashMap<Integer, Point> getDw_point_hash() {
            return dw_point_hash;
        }

        public HashMap<Integer, Point> getUp_point_hash() {
            return up_point_hash;
        }

        public Point getExactPoint(Point x,Point y){
            return  new Point(x.x,y.y);
        }

        public Point getPoint(int value, Column column){
            if (column  == getUpColumn()){
                return up_point_hash.get(value);
            }else if(column == getDwColumn()){
                return dw_point_hash.get(value);
            }else {
                return new Point(0,0);
            }
        }
        HashMap<Integer,Point> data_var_hash = new HashMap<>();
        public void addGraphicPoint(int key,Point point){
            data_var_hash.put(key,point);
        }

        public HashMap<Integer, Point> getData_var_hash() {
            return data_var_hash;
        }

        public void handleGraphicPoint(Graphics g){
            for (Point point:
                 data_var_hash.values()) {
                g.drawOval(point.x, point.y,10,10);
            }
        }

        Column dw_col;
        Column up_col;
        GraphicColumn column;
        public DataGraphic(GraphicColumn column){
            this.column = column;
            up_col = new Column(column.point.x,column.point.y,column.rect.width,column.rect.height);
            dw_col = new Column(column.point.x,column.point.y+column.rect.height,column.rect.height,column.rect.width);
        }
        public void drawDataGraphic(Graphics g){
            drawGraphic(g,column);
            handleIntVars(g);
            handleGraphicPoint(g);
            for (Point point:
                 oval_points) {
                g.drawOval(point.x,point.y,10,10);
            }
            drawLinesOnPoints(g);
        }
        public void handleIntVars(Graphics g){


          List<Integer> dw_int_list =   getDwColumn().field.getLastList();
          int i = 0;
            for (Integer integer:
                 dw_int_list) {
                g.drawString(integer+"",getDwColumn().x+i, getDwColumn().y+20);
                dw_point_hash.put(integer,new Point(getDwColumn().x+i, getDwColumn().y+20));
                int ps = getDwColumn().field.rate_of_inc*getDwColumn().height*2-60;
                if (ps<0){
                    ps = +ps;
                }
                i+= ps;

            }
            List<Integer> up_int_list = getUpColumn().field.getLastList();
            int j = 0;
            for (Integer integer_2:
                 up_int_list) {
                g.drawString(integer_2+"",getUpColumn().x-30, getDwColumn().y-j);
                up_point_hash.put(integer_2,new Point(getUpColumn().x-30, getDwColumn().y-j ));
                j+=getUpColumn().field.rate_of_inc/1.1;
            }
           // System.out.println("up hash : "+up_point_hash.toString());
           // System.out.println("dw hash : "+dw_point_hash.toString());
        }
        public List<HashMap<Integer,Point>> handlePoints(){
            List<Integer> dw_int_list =   getDwColumn().field.getLastList();
            int i = 0;
            for (Integer integer:
                    dw_int_list) {
                dw_point_hash.put(integer,new Point(getDwColumn().x+i, getDwColumn().y+20));
                i+= getDwColumn().field.rate_of_inc*getDwColumn().height*2-60;
            }
            List<Integer> up_int_list = getUpColumn().field.getLastList();
            int j = 0;
            for (Integer integer_2:
                    up_int_list) {
                up_point_hash.put(integer_2,new Point(getUpColumn().x-30, getDwColumn().y-j ));
                j+=getUpColumn().field.rate_of_inc/1.1;
            }
            List<HashMap<Integer,Point>> list = new ArrayList<>();
            list.add(dw_point_hash);
            list.add(up_point_hash);
            return list;
        }
        public void drawGraphic(Graphics g,GraphicColumn column){
            g.drawRect(column.point.x,column.point.y,column.rect.width,column.rect.height);
            g.drawRect(column.point.x,column.point.y+column.rect.height,column.rect.height,column.rect.width);
            init(g);
        }
        public Column getUpColumn(){
            return up_col;
        }
        public Column getDwColumn(){
            return dw_col;
        }
        public void addDataField(DataField field,Column column){
            if (column==getDwColumn()){
                getDwColumn().setField(field);
            }else if (column==getUpColumn()){
                getUpColumn().setField(field);
            }else {
                return;
            }
        }
        public void init(Graphics g){
            handle_data(g,getUpColumn(),null);
            handle_data(g,getDwColumn(), ColType.DOWN);
        }
        public void handle_data(Graphics g,Column column,ColType NULLABLE_TYPE){
            if (column == null){
                return;
            }
            if (column.field == null){
                return;
            }
            if (NULLABLE_TYPE== ColType.DOWN){
                g.drawString(column.field.NAME,column.width-column.x+50,column.y);
            }else{
                g.drawString(column.field.NAME,column.x,column.y);
            }
        }

        public Point translate(int dw_value,int up_value){
            Point rtr_point= this.getExactPoint(this.handlePoints().get(DW_HASH_INT).get(dw_value),this.handlePoints().get(UP_HASH_INT).get(up_value));
            rtr_point.setGet_valueDW(dw_value);
            rtr_point.setGet_value_Up(up_value);
            return rtr_point;

        }
        public void addAPoint(int dw_val,int up_val){
            oval_points.add(translate(dw_val,up_val));
        }

        public void drawLinesOnPoints(Graphics g){
            Point next_point = null;
            Point current_point = null;
            for (int i = 0; i<oval_points.size(); i++)
            {
                if (checkHasNext(oval_points,i+1)){
                next_point = oval_points.get(i+1);
                }

               current_point = oval_points.get(i);

               g.drawLine(current_point.x,current_point.y,next_point.x,next_point.y);

               g.drawString(getDwColumn().field.NAME+" =  "+next_point.get_value_Dw,next_point.x+10, next_point.y-100);
                g.drawString(getUpColumn().field.NAME+" =  "+next_point.get_value_Up,next_point.x+10, next_point.y-75);

                int up_cost = Math.max(current_point.get_value_Up,next_point.get_value_Up)-Math.min(current_point.get_value_Up,next_point.get_value_Dw);
                g.drawString("gap on "+getUpColumn().field.NAME+":"+up_cost,next_point.x+10, next_point.y-50);
               int dw_cost = Math.max(current_point.get_value_Dw,next_point.get_value_Dw)-Math.min(current_point.get_value_Dw,next_point.get_value_Dw);
               g.drawString("gap on "+getDwColumn().field.NAME+": "+dw_cost,next_point.x+10, next_point.y);
            }
        }
        public boolean checkHasNext(List<?> list,int i){
            try{
             list.get(i);
             return true;
            }catch (IndexOutOfBoundsException e){
                return false;
            }
        }

        public void init_(Graphics g){
            int count = getDwColumn().field.last;
            for (int i = 0; i < count; i++) {
                g.drawString(i+"",i*20,300);
            }

        }

    }
    public enum ColType{
        DOWN,
        UP;
    }
    public  static class Column{
        int x, y, width, height;
        DataField field;

        public void setField(DataField field) {
            this.field = field;
        }

        public Column(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

    }
    public static class DataField{
        String NAME;
        int last;
        int rate_of_inc;
        public DataField(String NAME,int last,int rate_of_inc){
            this.NAME = NAME;
            this.last = last;
            this.rate_of_inc = rate_of_inc;
        }
        public List<Integer> getLastList(){
            List<Integer> return_last = new ArrayList<>();
            int add = 0;
            for (int i = 0; i < last/rate_of_inc+1; i++) {
                return_last.add(add);
                add +=rate_of_inc;
            }
            return return_last;
        }
        public void append(DataField field){
        }
    }
    public static void main(String[] args) {
      DataGraphic dataGraphic = new DataGraphic(new GraphicColumn(new Rect(5,900),new Point(50,50)));
      Window window = new Window("test",dataGraphic);

      dataGraphic.addDataField(new DataField("time/day",200,10),dataGraphic.getDwColumn());
      dataGraphic.addDataField(new DataField("bank_vault_value/dollar $",1000,50),dataGraphic.getUpColumn());


      dataGraphic.addAPoint(10,100);
      dataGraphic.addAPoint(50,150);
      dataGraphic.addAPoint(100,450);
      dataGraphic.addAPoint(150,400);
      dataGraphic.addAPoint(170,850);






    }
}
