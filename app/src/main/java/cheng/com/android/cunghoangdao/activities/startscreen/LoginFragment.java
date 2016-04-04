package cheng.com.android.cunghoangdao.activities.startscreen;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.model.Common;
import cheng.com.android.cunghoangdao.fragments.BaseFragment;
import cheng.com.android.cunghoangdao.ultils.SharedPreferencesUser;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by Welcome on 1/19/2016.
 */
public class LoginFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    public static final String DATEPICKER_TAG = "datepicker";
    private Button btnDayOfBirth, btnSignup;
    private TextView tvHello;
    private EditText edtName;
    private RelativeLayout rlShow;
    private ProgressBar prgBar;
    private DatePickerDialog datePickerDialog;
    private int year, month, day;
    private OnLoginFragmentListener mOnLoginFragmentListener;

    @Override
    public void init() {
        final Calendar calendar = java.util.Calendar.getInstance();
        btnSignup = (Button) getView().findViewById(R.id.fragment_signin_btnsignup);
        tvHello = (TextView) getView().findViewById(R.id.fragment_signin_tvHello);
        edtName = (EditText) getView().findViewById(R.id.fragment_signin_edtName);
        rlShow = (RelativeLayout) getView().findViewById(R.id.rlShow);
        prgBar = (ProgressBar) getView().findViewById(R.id.fragment_signin_progressBar);
        btnDayOfBirth = (Button) getView().findViewById(R.id.fragment_signin_btnDateOfBirth);
        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(java.util.Calendar.YEAR),
                calendar.get(java.util.Calendar.MONTH), calendar.get(java.util.Calendar.DAY_OF_MONTH));
    }

    @Override
    public void setEvent() {
        btnDayOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.setVibrate(true);
                datePickerDialog.setYearRange(1985, 2028);
                datePickerDialog.show(getFragmentManager(), DATEPICKER_TAG);
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rlShow.getVisibility()==View.VISIBLE) {
                    Common.user.setUserName(edtName.getText() + "");
                    Common.user.setYear(year);
                    Common.user.setMonth(month);
                    Common.user.setDao(day);
                    SharedPreferencesUser.getInstance(getActivity()).saveInfo();
                    mOnLoginFragmentListener.onSignUpSuccess();
                }else {
                    showMessage("Lỗi thông tin","Vui lòng điền thông tin chính xác");
                }

            }
        });
    }

    @Override
    public void setValue() {
        mOnLoginFragmentListener = (OnLoginFragmentListener) getActivity();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_signin;
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, final int year, final int month, final int day) {

        this.year = year;
        this.month = month+1;
        this.day = day;
        if (edtName.getText().toString().trim().length() < 3 || edtName.getText().toString().trim().length()> 15) {
            if(rlShow.getVisibility()==View.VISIBLE){
                rlShow.setVisibility(View.GONE);
            }
            edtName.setError("Tên không hợp lệ! Tên phải ít nhất 3 kí tự và nhỏ hơn 15 kí tự");
        } else {
            rlShow.setVisibility(View.VISIBLE);
            tvHello.setText("Xin Chào " + edtName.getText() + "! Bạn sinh ngày " + day + "/" +( month + 1) + " thuộc cung "
                    + Common.checkCungHoangDao(month + 1, day) + " Hãy cùng khám phá những thông tin thú vị về bạn nào!");
        }


    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i1) {

    }
    public interface OnLoginFragmentListener{
        public void onSignUpSuccess();
    }
    public void showMessage(String title, String message){
        final MaterialDialog dialog = new MaterialDialog(getActivity());
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setPositiveButton("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
