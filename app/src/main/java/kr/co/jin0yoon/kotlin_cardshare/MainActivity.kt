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
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //타이틀을 숨긴다.
        setHideTitle()
        setContentView(R.layout.activity_main)

        //퍼미션을 사용자가 눈으로 손으로 확인하게 하는 함수
        //구글 정책. 구글이 정책으로 만들면 개발자는 무조건 해야 함.
        grantExternalStoragePermission()
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
}