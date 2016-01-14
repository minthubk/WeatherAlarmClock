/*
 * Copyright (c) 2016. Kaku咖枯 Inc. All rights reserved.
 */
package com.kaku.weac.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaku.weac.R;
import com.kaku.weac.bean.ImageBucket;
import com.kaku.weac.common.WeacConstants;
import com.kaku.weac.util.ImageLoaderHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 本地相册适配器
 *
 * @author 咖枯
 * @version 1.0 2016/1/13
 */
public class LocalAlbumAdapter extends ArrayAdapter<ImageBucket> {

    private Context mContext;

    public LocalAlbumAdapter(Context context, List<ImageBucket> localAlbumList) {
        super(context, 0, localAlbumList);
        mContext = context;
        ImageLoader.getInstance().init(ImageLoaderHelper.getInstance(mContext)
                .getImageLoaderConfiguration(WeacConstants.IMAGE_LOADER_CACHE_PATH));

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.lv_local_album, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ImageBucket imageBucket = getItem(position);
        if (null != imageBucket) {
            // 图片path
            String imagePath = imageBucket.bucketList.get(0).getImagePath();
            if (!TextUtils.isEmpty(imagePath)) {
                ImageLoader.getInstance().displayImage("file://" + imagePath, viewHolder
                        .mAlbumThumbnailIv, ImageLoaderHelper.getInstance(mContext)
                        .getDisplayOptions());
            }

            int count = imageBucket.count;
            String title = imageBucket.bucketName;

            if (!TextUtils.isEmpty(title)) {
                viewHolder.mAlbumTitleTv.setText(String.format(
                        mContext.getString(R.string.picture_name_number), title, count));
            }
        }
        return convertView;
    }

    public class ViewHolder {
        public final ImageView mAlbumThumbnailIv;
        public final TextView mAlbumTitleTv;

        public ViewHolder(View root) {
            mAlbumThumbnailIv = (ImageView) root.findViewById(R.id.album_thumbnail_iv);
            mAlbumTitleTv = (TextView) root.findViewById(R.id.album_title_tv);
        }
    }
}
