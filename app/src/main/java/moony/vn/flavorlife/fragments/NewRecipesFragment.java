package moony.vn.flavorlife.fragments;

import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ntq.api.model.OnDataChangedListener;

import moony.vn.flavorlife.FlavorLifeApplication;
import moony.vn.flavorlife.R;
import moony.vn.flavorlife.api.model.DfeGetNewRecipes;
import moony.vn.flavorlife.api.model.DfeRegisterGcm;
import moony.vn.flavorlife.api.model.FlPaginatedList;
import moony.vn.flavorlife.entities.User;
import moony.vn.flavorlife.gcm.GcmUtils;

/**
 * Created by moony on 3/1/15.
 */
public class NewRecipesFragment extends FlListFragment {
    private DfeRegisterGcm mDfeRegisterGcm;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_new_recipes;
    }

    @Override
    protected FlPaginatedList getFlPaginatedList() {
        return new DfeGetNewRecipes(mApi);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionbar.syncActionBar(this);
        if (FlavorLifeApplication.get().getUser().getState() == User.State.LOGGED_OUT) {
            requestGcm(GcmUtils.getRegistrationId(getActivity()));
            FlavorLifeApplication.get().updateStateUser(true);
        }
    }

    private void requestGcm(String registerId) {
        if (mDfeRegisterGcm == null) {
            mDfeRegisterGcm = new DfeRegisterGcm(FlavorLifeApplication.get().getDfeApi());
            mDfeRegisterGcm.addDataChangedListener(new OnDataChangedListener() {
                @Override
                public void onDataChanged() {
                    System.out.println("Mj : success");
                }
            });
            mDfeRegisterGcm.addErrorListener(new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("Mj : error - " + error.toString());
                }
            });
        }

        mDfeRegisterGcm.makeRequest(FlavorLifeApplication.get().getUser().getId(), registerId);
    }

}
