//package cn.it.eventlistener;
//
//import android.app.Activity;
//import android.graphics.Bitmap;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.view.KeyEvent;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.inputmethod.EditorInfo;
//import android.widget.AdapterView;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import com.example.administrator.my_shoujiyingyin.R;
//
//public class EventActivity extends Activity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_event);
//        //1.通过CheckBox实现明密文切换
//        changeInfo();
//        //2. 通过RadioGroup与RadioButton选择字符集
//        choiceCharset();
//
//        //3. 通过Spinner 下拉选择天气
//        selectWeather();
//
//        //4. 通过长按设置手机壁纸
//        setPaper();
//    }
//    // 4. 通过长按设置手机壁纸
//    private void setPaper() {
//        final ImageView paperImageView=(ImageView)findViewById(R.id.paper_iv);
//        //1.设置 长按监听  当按住控件 2秒左右，则识别为长按
//        paperImageView.setOnLongClickListener(new View.OnLongClickListener() {
//
//            @Override
//            public boolean onLongClick(View v) {
//                System.out.println("onLongClick");
//
//                //长按设置手机壁纸
//                try {
//                    clearWallpaper();//清除手机壁纸
//                    /**Drawable  :可绘制的资源，包含 Bitmap、图层，可绘制的颜色资源等，它是一个抽象类
//                     *
//                     */
//                    //					Drawable drawable = getResources().getDrawable(R.drawable.ic_girl1);
//                    //					BitmapDrawable bitmapDrawable=(BitmapDrawable) drawable;
//                    //					Bitmap bitmap=bitmapDrawable.getBitmap(); //Bitmap是位图格式文件，以像素的形式来描述图片 ，比如png，jpg,bmp,gif 都是
//                    //					setWallpaper(bitmap);
//
//                    //通过图片控件前景图片属性来设置手机壁纸
//                    Drawable drawable = paperImageView.getDrawable();//取得前景图片Drawable
//                    //					Drawable background = paperImageView.getBackground();//取背景
//                    BitmapDrawable bitmapDrawable=(BitmapDrawable) drawable;
//                    Bitmap bitmap=bitmapDrawable.getBitmap(); //Bitmap是位图格式文件，以像素的形式来描述图片 ，比如png，jpg,bmp,gif 都是
//                    setWallpaper(bitmap);
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                return true;//返回false，表示当前的请求没有耗尽，android框架还会继续的回调其他的监听方法
//                //返回true，表示当前的请求已经结束，android框架不会继续的回调其他的监听方法
//            }
//        });
//        //2.设置 触摸监听
//        paperImageView.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                System.out.println("onTouch");
//                return false;
//            }
//        });
//        //3.设置 点击监听
//        paperImageView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                System.out.println("onClick");
//            }
//        });
//
//
//    }
//
//    //3. 通过Spinner 下拉选择天气
//    private void selectWeather() {
//        //取资源
//        final String[] weathers = this.getResources().getStringArray(R.array.weathers);
//        Spinner spinner=(Spinner)findViewById(R.id.weather_spinner);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            /**有下拉项选择回调该方法
//             * parent： AdapterView ： Spinner
//             *    AdapterView 与adapter展示一个动态的布局
//             *       AdapterView:展示数据
//             *       Adapter：装配数据
//             * view ：View  ：表示当前选择的下拉项视图对象
//             * position ： 当前选择的下拉项的数据在适配器中的位置
//             * id：当前选择的下拉项数据的行号id属性
//             */
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int position, long id) {
//                // spinner 展示数据  adapte装配数据
//                //依据当前选择的下拉项的数据在适配器中的位置来获取数据   数据是封装在数组中
//                String selectWeather=weathers[position];
//                Toast.makeText(getApplicationContext(), selectWeather, Toast.LENGTH_SHORT).show();
//
//            }
//            //什么都没有选择
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // TODO Auto-generated method stub
//
//            }
//        });
//
//    }
//    //2. 通过RadioGroup与RadioButton选择字符集
//    private void choiceCharset() {
//        RadioGroup  radioGroup=(RadioGroup) findViewById(R.id.radioGroup1);
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            /**当单选组的单选按钮的状态改变时回调该方法
//             * group： RadioGroup
//             * checkedId：选择的控件(单选按钮)的资源id
//             */
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                //				RadioButton radioButton=(RadioButton)findViewById(checkedId);
//                //				RadioButton radioButton=(RadioButton)EventActivity.this.findViewById(checkedId);
//                // View.findViewById,即 Activity关联的布局的根节点的View 来findViewById
//                //通过父控group来查找子控件 RadioButton
//                RadioButton radioButton=(RadioButton)group.findViewById(checkedId);
//                Toast.makeText(getApplicationContext(), radioButton.getText().toString()+"group", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//    // 1. 通过CheckBox实现明密文切换
//    private void changeInfo() {
//        final EditText infoEditText=(EditText)findViewById(R.id.info_et);
//        CheckBox changeInfoChx=(CheckBox)findViewById(R.id.change_info_chx);
//        //对复选控件设置状态改变的监听 ，实现复选效果
//        changeInfoChx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//            /**当复选框状态改变时，回调该方法
//             * buttonView： CompoundButton：(抽象类)      CheckBox
//             * isChecked: 是否选择
//             */
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView,
//                                         boolean isChecked) {
//                //明密文切换
//                if(isChecked){//显示明文
//                    //					infoEditText.setInputType(EditorInfo.TYPE_NULL);//没有格式，没有类型
//                    infoEditText.setInputType(EditorInfo.TYPE_CLASS_TEXT);//以文本显示
//
//                }else{
//                    //显示密文 ，不是加密  ，以****来替换内容
//                    //					infoEditText.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);  // 16进制  0x80 --》 10进制   128
//                    infoEditText.setInputType(129);  // 16进制  0x80 --》 10进制   128
//                }
//            }
//
//        });
//
//
//    }
//
//    //按键事件  keyCode:按键码
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        System.out.println("onKeyDown" +keyCode);
//        if(keyCode==4){//按下返回键
//            if(System.currentTimeMillis()-currentTime>2000){
//                Toast.makeText(getApplicationContext(), "再按一次退出系统", Toast.LENGTH_SHORT).show();
//                currentTime=System.currentTimeMillis();
//            }else{
//                exitSystem();
//            }
//            //			finish();
//        }
//        return true;
//        //		return super.onKeyDown(keyCode, event);
//    }
//    //退出系统
//    private void exitSystem() {
//        finish();//销毁当前的Activity
//    }
//    //按下返回按键事件
//    @Override
//    public void onBackPressed() {
//        System.out.println("onBackPressed");
//        //	  finish(); 表示销毁当前的Activity
//        super.onBackPressed();
//    }
//
//    private long currentTime=0;//保存当前的时间
//
//
//}
