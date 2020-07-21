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

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //타이틀을 숨긴다.
        setHideTitle()
        setContentView(R.layout.activity_main)
    }

    private fun setHideTitle(){
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getSupportActionBar()!!.hide()
    }
}