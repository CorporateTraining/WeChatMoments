package com.example.wechatmoments.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.wechatmoments.R;
import com.example.wechatmoments.repository.entity.User;
import com.example.wechatmoments.repository.entity.WeChatMoment;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import java.util.List;

import static com.jaeger.ninegridimageview.NineGridImageView.STYLE_FILL;


public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<WeChatMoment> weChatMoments;
    private User user;
    private WeChatViewModel weChatViewModel;
    private Context context;
    private RequestManager requestManager;
    public static final int TYPE_HEADER = 0;

    public MyAdapter(List<WeChatMoment> weChatMoments, WeChatViewModel weChatViewModel, User user) {
        this.weChatMoments = weChatMoments;
        this.weChatViewModel = weChatViewModel;
        this.user = user;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        switch (viewType) {
            case TYPE_HEADER:
                View header = LayoutInflater.from(context).inflate(R.layout.activity_we_chat_header, parent, false);
                return new HeaderViewHolder(header);
            default:
                View titleView = LayoutInflater.from(context).inflate(R.layout.activity_we_chat_tweets_item, parent, false);
                return new MyViewHolder(titleView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        requestManager = Glide.with(context);
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                displayWeChatHeader((HeaderViewHolder) holder);
                break;
            default:
                displayWeChatTweets((MyViewHolder) holder, position);
                break;
        }
    }

    private void displayWeChatHeader(@NonNull HeaderViewHolder holder) {
        holder.username.setText(user.getNick());
        requestManager.load(user.getAvatar())
                .centerCrop()
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .into(holder.headerAvatar);
        requestManager.load(user.getProfileImage())
                .centerCrop()
                .into(holder.profileImage);
    }

    private void displayWeChatTweets(@NonNull MyViewHolder holder, int position) {
        WeChatMoment weChatMoment = weChatMoments.get(position - 1);
        User sender = weChatMoment.getSender();
        displayAvatarAndUserName(holder, sender);
        displayContent(holder, weChatMoment);
        displayImages(holder, weChatMoment);
    }

    private void displayAvatarAndUserName(@NonNull MyViewHolder holder, User sender) {
        requestManager.load(sender.getAvatar())
                .centerCrop()
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .into(holder.avatar);
        holder.senderUsername.setText(sender.getNick());
    }

    private void displayImages(@NonNull MyViewHolder holder, WeChatMoment weChatMoment) {
        List<String> images = weChatMoment.getImages();
        if (images != null) {
            holder.nineGridImageView.setVisibility(View.VISIBLE);
            displayGridImageView(holder, images);
        } else {
            holder.nineGridImageView.setVisibility(View.GONE);
        }
    }

    private void displayContent(@NonNull MyViewHolder holder, WeChatMoment weChatMoment) {
        String content = weChatMoment.getContent();
        if (content != null) {
            holder.senderContent.setVisibility(View.VISIBLE);
            holder.senderContent.setText(content);
        } else {
            holder.senderContent.setVisibility(View.GONE);
        }
    }

    private void displayGridImageView(@NonNull MyViewHolder holder, List<String> images) {
        NineGridImageViewAdapter<String> mAdapter = new NineGridImageViewAdapter<String>() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, String url) {
                requestManager.load(url)
                        .into(imageView);
            }

            @Override
            protected ImageView generateImageView(Context context) {
                return super.generateImageView(context);
            }
        };
        holder.nineGridImageView.setImagesData(images);
        holder.nineGridImageView.setAdapter(mAdapter);
        holder.nineGridImageView.setMaxSize(images.size());
        holder.nineGridImageView.setShowStyle(STYLE_FILL);
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return position;
    }

    @Override
    public int getItemCount() {
        return weChatMoments.size() + 1;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView senderUsername, senderContent;
        private ImageView avatar;
        private NineGridImageView nineGridImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.senderUsername = itemView.findViewById(R.id.sender_username);
            this.senderContent = itemView.findViewById(R.id.sender_content);
            this.avatar = itemView.findViewById(R.id.tweets_avatar);
            this.nineGridImageView = itemView.findViewById(R.id.images_view);
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

}
