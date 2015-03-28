package moony.vn.flavorlife.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.RequestCode;

/**
 * Created by moony on 3/27/15.
 */
public class ChooseTypeRecipeDialogFragment extends DialogFragment implements View.OnClickListener {
    private ImageView mType1, mType2, mType3;
    private Button mOk;
    private List<ImageView> mListTypes = new ArrayList<ImageView>();
    private List<Integer> mListTypeChoose = new ArrayList<Integer>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, STYLE_NO_TITLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_choose_type, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mType1 = (ImageView) view.findViewById(R.id.type_1);
        mType2 = (ImageView) view.findViewById(R.id.type_2);
        mType3 = (ImageView) view.findViewById(R.id.type_3);
        mListTypes.add(mType1);
        mListTypes.add(mType2);
        mListTypes.add(mType3);
        view.findViewById(R.id.ok).setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        for (int i = 0; i < mListTypes.size(); i++) {
            final int position = i;
            mListTypes.get(i).getDrawable().setAlpha(40);
            mListTypes.get(i).setTag(R.drawable.ic_launcher);
            mListTypes.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ((Integer) mListTypes.get(position).getTag() == R.drawable.ic_launcher) {
                        mListTypes.get(position).setImageResource(R.drawable.ic_launcher_2);
                        mListTypes.get(position).setTag(R.drawable.ic_launcher_2);
                    } else {
                        mListTypes.get(position).setImageResource(R.drawable.ic_launcher);
                        mListTypes.get(position).setTag(R.drawable.ic_launcher);
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        Intent data = new Intent();
        for (int i = 0; i < mListTypes.size(); i++) {
            if ((Integer) mListTypes.get(i).getTag() == R.drawable.ic_launcher_2) {
                mListTypeChoose.add(i);
            }
        }
        data.putIntegerArrayListExtra("list_types", new ArrayList<Integer>(mListTypeChoose));
        getTargetFragment().onActivityResult(RequestCode.CODE_CHOOSE_TYPE, Activity.RESULT_OK, data);
        dismiss();
    }
}
