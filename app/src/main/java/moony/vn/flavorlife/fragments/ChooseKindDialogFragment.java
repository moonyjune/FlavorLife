package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.adapters.ChapterAdapter;
import moony.vn.flavorlife.adapters.KindAdapter;
import moony.vn.flavorlife.entities.Chapter;
import moony.vn.flavorlife.entities.Kind;

/**
 * Created by moony on 4/30/15.
 */
public class ChooseKindDialogFragment extends DialogFragment {
    private static final int NUM_KIND = 3;
    private static final String[] kindNames = new String[]{"Starter", "Main course", "Desserts"};
    private static final int[] kindTypes = new int[]{0, 1, 2};
    private ListView mListViewKinds;
    private KindAdapter mKindAdapter;
    private List<Kind> mListKinds;

    public static ChooseKindDialogFragment newInstance() {
        ChooseKindDialogFragment chooseChapterDialogFragment = new ChooseKindDialogFragment();
        Bundle bundle = new Bundle();
        chooseChapterDialogFragment.setArguments(bundle);
        return chooseChapterDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, STYLE_NORMAL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_choose_kind, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListViewKinds = (ListView) view.findViewById(R.id.list);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mListKinds == null) {
            mListKinds = new ArrayList<Kind>();
            for (int i = 0; i < NUM_KIND; i++) {
                Kind kind = new Kind();
                kind.setName(kindNames[i]);
                kind.setKind(kindTypes[i]);
                kind.setChose(false);
                mListKinds.add(kind);
            }
        }
        if (mKindAdapter == null)
            mKindAdapter = new KindAdapter(getActivity(), 0, mListKinds);
        mListViewKinds.setAdapter(mKindAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        int height = getResources().getDimensionPixelSize(R.dimen.dialog_height);
        int width = getResources().getDimensionPixelSize(R.dimen.dialog_width);
        getDialog().getWindow().setLayout(width, height);
    }

}
