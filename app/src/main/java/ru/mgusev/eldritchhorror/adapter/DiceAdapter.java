package ru.mgusev.eldritchhorror.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.iigo.library.DiceLoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.interfaces.OnItemClickedReturnObj;
import ru.mgusev.eldritchhorror.model.Dice;
import ru.mgusev.eldritchhorror.support.DiceDiffUtilCallback;
import timber.log.Timber;

public class DiceAdapter extends MvpRecyclerListAdapter<Dice, DiceItemPresenter, DiceItemViewHolder> {

    @NonNull
    @Override
    public DiceItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DiceItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dice, parent, false));
    }

    @NonNull
    @Override
    protected DiceItemPresenter createPresenter(@NonNull Dice dice) {
        DiceItemPresenter presenter = new DiceItemPresenter();
        presenter.setModel(dice);
        return presenter;
    }

    @NonNull
    @Override
    protected Object getModelId(@NonNull Dice model) {
        return model.getId();
    }


    private Context context;
    private List<Dice> diceList;
    private OnItemClickedReturnObj onClick;
    private boolean isAnimation;

//    public DiceAdapter(MvpDelegate<?> parentDelegate) {
//        super(parentDelegate, String.valueOf(0));
//
//        //mScrollToBottomListener = scrollToBottomListener;
//        diceList = new ArrayList<>();
////        mLiked = new ArrayList<>();
////        mLikesInProgress = new ArrayList<>();
//    }
//
//
//
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return diceList.get(position).getId();
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//
//    @Override
//    public void setState(boolean isInProgress, boolean isLiked) {
//
//    }
//
//
//
//
//
//
//    public class DiceViewHolder implements MvpView {
//
//        @InjectPresenter
//        DiceItemPresenter mRepositoryPresenter;
//
//        private Dice dice;
//
//        @BindView(R.id.item_dice_dlv)
//        DiceLoadingView diceDLV;
//        View view;
//
//        private MvpDelegate mMvpDelegate;
//
//        @ProvidePresenter
//        DiceItemPresenter provideRepositoryPresenter() {
//            return new DiceItemPresenter(dice);
//        }
//
//        DiceViewHolder(View view) {
//            this.view = view;
//
//            ButterKnife.bind(this, view);
//        }
//
//        void bind(int position, Dice dice) {
//            if (getMvpDelegate() != null) {
//                getMvpDelegate().onSaveInstanceState();
//                getMvpDelegate().onDetach();
//                getMvpDelegate().onDestroyView();
//                mMvpDelegate = null;
//            }
//
//            this.dice = dice;
//
//            getMvpDelegate().onCreate();
//            getMvpDelegate().onAttach();
//
//            diceDLV.setFirstSideDiceNumber(dice.getFirstValue());
//            diceDLV.setSecondSideDiceNumber(dice.getSecondValue());
//            diceDLV.setThirdSideDiceNumber(dice.getThirdValue());
//            diceDLV.setFourthSideDiceNumber(dice.getFourthValue());
//
////            view.setBackgroundResource(position == mSelection ? R.color.colorAccent : android.R.color.transparent);
////
////            likeImageButton.setOnClickListener(v -> mRepositoryLikesPresenter.toggleLike(repository.getId()));
////
////            boolean isInProgress = mLikesInProgress.contains(repository.getId());
////
////            likeImageButton.setEnabled(!isInProgress);
////            likeImageButton.setSelected(mLiked.contains(repository.getId()));
//        }
//
////        @Override
////        public void showRepository(Repository repository) {
////            nameTextView.setText(repository.getName());
////        }
////
////        @Override
////        public void updateLike(boolean isInProgress, boolean isLiked) {
////            // pass
////        }
//
//        MvpDelegate getMvpDelegate() {
//            if (dice == null) {
//                return null;
//            }
//
//            if (mMvpDelegate == null) {
//                mMvpDelegate = new MvpDelegate<>(this);
//                mMvpDelegate.setParentDelegate(DiceAdapter.this.getMvpDelegate(), String.valueOf(dice.getId()));
//
//            }
//            return mMvpDelegate;
//        }
//    }
//
//
//
//
//
//
////    static class DiceItemViewHolder extends Mvp.ViewHolder {
////        @BindView(R.id.item_dice_dlv)
////        DiceLoadingView diceDLV;
////
////        private Dice item;
////
////        DiceItemViewHolder(View itemView) {
////            super(itemView);
////            ButterKnife.bind(this, itemView);
////        }
////    }
//
//
//
////    public DiceAdapter(Context context) {
////        //App.getComponent().inject(this);
////        this.context = context;
////        this.diceList = new ArrayList<>();
////    }
//
//    public void setData(List<Dice> list) {
//        Timber.d("UPDATE ADAPTER");
//        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiceDiffUtilCallback(this.diceList, list), false);
//        diceList.clear();
//        diceList.addAll(list);
//        notifyDataSetChanged();
//        //diffResult.dispatchUpdatesTo(this);
//    }
//
////    @NonNull
////    @Override
////    public DiceAdapter.DiceItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dice, parent, false);
////        return new DiceAdapter.DiceItemViewHolder(view);
////    }
////
////    @Override
////    public void onBindViewHolder(@NonNull final DiceAdapter.DiceItemViewHolder holder, int position) {
////        Timber.d(String.valueOf(diceList.get(position).getFirstValue()));
////        holder.item = diceList.get(position);
////        //holder.item.setDiceDLV(holder.diceDLV);
////
////
////
////        holder.diceDLV.setFirstSideDiceNumber(holder.item.getFirstValue());
////        holder.diceDLV.setSecondSideDiceNumber(holder.item.getSecondValue());
////        holder.diceDLV.setThirdSideDiceNumber(holder.item.getThirdValue());
////        holder.diceDLV.setFourthSideDiceNumber(holder.item.getFourthValue());
////
////        if (holder.item.isAnimationRun()) {
////            holder.diceDLV.setDuration(3000);
////            holder.diceDLV.start();
////        } else if (holder.item.isAnimationFinish()) {
////            holder.diceDLV.setDuration(0);
////            holder.diceDLV.stop();
////        }
////
////
////        holder.diceDLV.setOnClickListener(v -> onClick.onItemClick(holder.item));
////
////
////    }
//
////    @Override
////    public void onViewRecycled(@NonNull DiceItemViewHolder holder) {
////        super.onViewRecycled(holder);
////        holder.item = null;
////    }
////
////    @Override
////    public int getItemCount() {
////        return diceList.size();
////    }

    public void setOnClick(OnItemClickedReturnObj onClick) {
        this.onClick = onClick;
    }
}