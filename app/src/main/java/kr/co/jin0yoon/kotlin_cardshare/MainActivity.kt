package kr.co.jin0yoon.kotlin_cardshare

/*
onCreate(Bundle?):Unit                 -> 화면 설정 및 이벤트 핸들러, 퍼미션 요청
setHideTitle():Unit                    -> 타이틀바 없애기
grantExternalStoragePermission():Unit  -> 퍼미션 요청
SaveImage():File                       -> EditText를 이미지로 만들어 파일로 변환
SelectBackground():Unit                -> 배경 선택
ShareImage(File):Unit                  -> File 공유하기
DeleteTextAndImaeFile():Unit           -> EditText 및 File 지우기
 */

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.Window
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //타이틀을 숨긴다.
        setHideTitle()
        setContentView(R.layout.activity_main)

        //퍼미션을 사용자가 눈으로 손으로 확인하게 하는 함수
        //구글 정책. 구글이 정책으로 만들면 개발자는 무조건 해야 함.
        grantExternalStoragePermission()

        //버튼을 가져오고, click 핸들러를 구현한다.
        btnShare.setOnClickListener{
            //EditText의 화면을 File로 저장하고
            //저장된 파일을 공유한다.
            SaveImage().let{ShareImage(it)}
        }
    }

    private fun setHideTitle(){
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getSupportActionBar()!!.hide()
    }

    //0. 마시멜로우 버전 이후부터는 정책상 반드시 해야 하는 과정
    private fun grantExternalStoragePermission():Boolean {
        //마시멜로우 버전 이상이면
        return if (Build.VERSION.SDK_INT >= 23){
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                true
            }else{
                //퍼미션이 허락되어있지 않다면 허락을 요청하는 창을 띄운다.
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
                false
            }
        }else{
            true
        }
    }

    //2. EditText의 화면을 File로 저장하는 메소드 구현
    fun SaveImage(): File {

        val b:Bitmap
        editText1.apply {
            //Android에서 반드시 하라고 하는 규격임.
            //EditText의 이미지를 가져가려면 아래 2개의 메소드를 실행하며 true값을 넘겨주어야 함.
            isDrawingCacheEnabled = true
            buildDrawingCache(true)

            //et(EditText)의 메소드인 getDrawingCache()를 호출하고
            //그 결과값을 Bitmap클래스의 createBitmap()을 호출한다.
            //결과값은 Bitmap 객체이다.
            b = Bitmap.createBitmap(drawingCache)

            //Bitmap 객체 b를 얻었으므로 bt의 화면 메모리를 다시 닫아야 한다.
            //아래의 코드는 반드시 실행되어야 한다.
            isDrawingCacheEnabled = false
            buildDrawingCache(false)
        }

        //경로명을 만든다.
        val file_path = Environment.getExternalStorageDirectory().absolutePath + "/campandroid"

        //파일 객체를 가져오고
        val dir = File(file_path)

        //경로(파일)이 존재하는가?
        if (!dir.exists()){
            dir.mkdirs() //없다면 새롭게 생성
        }

        //경로명에 포함된 test.png이라는 이름의 파일을 생성
        val file = File(dir, "test.png")
        FileOutputStream(file).apply {
            //Bitmap을 압축 png 85%로
            b.compress(Bitmap.CompressFormat.PNG, 85, this)
            //파일 쓰기
            flush()
            //파일 닫기
            close()
        }

        //파일을 넘겨준다.
        return file
    }
}