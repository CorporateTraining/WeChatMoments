package com.example.wechatmoments.ui;

import android.content.Context;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.wechatmoments.R;
import com.example.wechatmoments.repository.entity.User;
import com.example.wechatmoments.repository.entity.WeChatMoment;
import com.example.wechatmoments.repository.entity.vo.Comment;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import java.util.List;

import static com.example.wechatmoments.ui.WeChatActivity.PAGE_COUNT;
import static com.jaeger.ninegridimageview.NineGridImageView.STYLE_FILL;


public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<WeChatMoment> weChatMoments;
    private User user;
    private WeChatViewModel weChatViewModel;
    private Context context;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_FOOTER = -1;
    private boolean hasMoreMoments;

    public MyAdapter(List<WeChatMoment> weChatMoments, WeChatViewModel weChatViewModel, User user) {
        this.weChatMoments = weChatMoments;
        this.weChatViewModel = weChatViewModel;
        this.user = user;
    }

    public void setHasMoreMoments(boolean hasMoreMoments) {
        this.hasMoreMoments = hasMoreMoments;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        switch (viewType) {
            case TYPE_HEADER:
                View header = LayoutInflater.from(context).inflate(R.layout.activity_we_chat_header, parent, false);
                return new HeaderViewHolder(header);
            case TYPE_FOOTER:
                View footerView = LayoutInflater.from(context).inflate(R.layout.activity_foot_view, parent, false);
                return new FooterViewHolder(footerView);
            default:
                View titleView = LayoutInflater.from(context).inflate(R.layout.activity_we_chat_moments_item, parent, false);
                return new MyViewHolder(titleView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                displayWeChatHeader((HeaderViewHolder) holder);
                break;
            case TYPE_FOOTER:
                displayWeChatFooter((FooterViewHolder) holder);
                break;
            default:
                displayWeChatMoments((MyViewHolder) holder, position);
                break;
        }
    }

    private void displayWeChatFooter(FooterViewHolder holder) {
        if (hasMoreMoments) {
            holder.footView.setText(R.string.footer_view_more);
            new Handler().postDelayed(() -> updateRecyclerView(getWeChatMomentsCount(), getWeChatMomentsCount() + PAGE_COUNT), 500);
        } else {
            holder.footView.setText(R.string.footer_view_no_more);
        }

    }

    public void updateRecyclerView(int fromIndex, int toIndex) {
        List<WeChatMoment> newWeChatMoments = weChatViewModel.getWeChatMoments(fromIndex, toIndex);
        if (newWeChatMoments != null) {
            updateList(newWeChatMoments, true);
        } else {
            updateList(null, false);
        }
    }


    private void displayWeChatHeader(HeaderViewHolder holder) {
        holder.username.setText(user.getNick());
        Glide.with(context)
                .load(user.getAvatar())
                .centerCrop()
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .into(holder.headerAvatar);
        Glide.with(context)
                .load(user.getProfileImage())
                .centerCrop()
                .into(holder.profileImage);
    }

    private void displayWeChatMoments(@NonNull MyViewHolder holder, int position) {
        WeChatMoment weChatMoment = weChatMoments.get(position - 1);
        User sender = weChatMoment.getSender();
        initGridImageView(holder);
        displayAvatarAndUserName(holder, sender);
        displayContent(holder, weChatMoment.getContent());
        displayImages(holder, weChatMoment.getImages());
        displayComments(holder, weChatMoment.getComments());
    }

    private void displayComments(MyViewHolder holder, List<Comment> comments) {
        if (comments != null && comments.size() > 0) {
            holder.commentsView.setVisibility(View.VISIBLE);
            for (Comment comment : comments) {
                TextView textview = new TextView(context);
                User sender = comment.getSender();
                textview.setText(Html.fromHtml(String.format("<font color='#5a6a91'><b>%s</b></font>: %s", sender.getNick(), comment.getContent()), Html.FROM_HTML_MODE_LEGACY));

                holder.commentsView.addView(textview);
            }
        } else {
            holder.commentsView.setVisibility(View.GONE);
        }
    }

    private void initGridImageView(@NonNull MyViewHolder holder) {
        holder.nineGridImageView.setShowStyle(STYLE_FILL);
        holder.nineGridImageView.setMaxSize(9);
        holder.nineGridImageView.setAdapter(new NineGridImageViewAdapter<String>() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, String url) {
                Glide.with(context)
                        .load(url)
                        .centerCrop()
                        .into(imageView);
            }
        });
    }

    private void displayAvatarAndUserName(@NonNull MyViewHolder holder, User sender) {
        Glide.with(context)
                .load(sender.getAvatar())
                .centerCrop()
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .into(holder.avatar);
        holder.senderUsername.setText(sender.getNick());
    }

    private void displayImages(@NonNull MyViewHolder holder, List<String> images) {
        if (images != null) {
            holder.nineGridImageView.setVisibility(View.VISIBLE);
            holder.nineGridImageView.setImagesData(images);
        } else {
            holder.nineGridImageView.setVisibility(View.GONE);
        }
    }

    private void displayContent(@NonNull MyViewHolder holder, String content) {
        if (content != null) {
            holder.senderContent.setVisibility(View.VISIBLE);
            holder.senderContent.setText(content);
        } else {
            holder.senderContent.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        if (position - 1 == weChatMoments.size()) {
            return TYPE_FOOTER;
        }
        return position;
    }

    @Override
    public int getItemCount() {
        return weChatMoments.size() + 2;
    }

    public int getWeChatMomentsCount() {
        return weChatMoments.size();
    }

    public void updateList(List<WeChatMoment> newWeChatMoments, boolean hasMoreMoments) {
        this.hasMoreMoments = hasMoreMoments;
        if (newWeChatMoments != null) {
            weChatMoments.addAll(newWeChatMoments);
        }
        new Handler().post(this::notifyDataSetChanged);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView senderUsername, senderContent;
        private ImageView avatar;
        private NineGridImageView nineGridImageView;
        private LinearLayout commentsView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.senderUsername = itemView.findViewById(R.id.sender_username);
            this.senderContent = itemView.findViewById(R.id.sender_content);
            this.avatar = itemView.findViewById(R.id.moments_avatar);
            this.nineGridImageView = itemView.findViewById(R.id.images_view);
            this.commentsView = itemView.findViewById(R.id.comments_view);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView username;
        private ImageView headerAvatar, profileImage;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            this.username = itemView.findViewById(R.id.username);
            this.headerAvatar = itemView.findViewById(R.id.header_avatar);
            this.profileImage = itemView.findViewById(R.id.profile_image);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        private TextView footView;

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            this.footView = itemView.findViewById(R.id.foot_view);
        }
    }

}
