package com.yoctopuce.yoctopucetoolbox.misc;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.hub.Hub;

import junit.framework.Assert;


public class HubViewHolder extends RecyclerView.ViewHolder
{
    private ImageView _iconImageView;
    private TextView _urlTextView;
    private TextView _firstTextView;
    private ImageView _editImageView;
    private Hub _hub;

    public HubViewHolder(View itemView)
    {
        super(itemView);
        _firstTextView = (TextView) itemView.findViewById(R.id.hub_line1);
        _urlTextView = (TextView) itemView.findViewById(R.id.hub_line2);
        _editImageView = (ImageView) itemView.findViewById(R.id.edit);
        _iconImageView = (ImageView) itemView.findViewById(R.id.icon);
        Assert.assertNotNull(_firstTextView);
        Assert.assertNotNull(_urlTextView);
    }

    public void bindHub(final Hub hub, final OnSelectListener selectListener, final OnEditListener editListener, final OnDeleteListener deleteListener)
    {
        _hub = hub;


        if (hub.isOnline()) {
            if (hub.isBeacon()) {
                _iconImageView.setImageResource(R.drawable.yocto_circle_blue_40_dp);
            } else {
                _iconImageView.setImageResource(R.drawable.yocto_circle_40_dp);
            }
        } else {
            _iconImageView.setImageResource(R.drawable.yocto_circle_40_dp);
        }
        _firstTextView.setText(hub.getDescription());
        _urlTextView.setText(_hub.getUrl());

        if (selectListener != null) {
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    selectListener.onSelect(_hub);
                }
            });
        } else {
            itemView.setOnClickListener(null);
        }

        if (editListener != null) {
            _editImageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    editListener.onEdit(_hub);
                }
            });
            _editImageView.setVisibility(View.VISIBLE);
        } else {
            _editImageView.setOnClickListener(null);
            _editImageView.setVisibility(View.GONE);
        }


        if (deleteListener != null) {
            itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View view)
                {
                    return deleteListener.onDelete(_hub);
                }
            });
        } else {
            itemView.setOnLongClickListener(null);
        }
    }


    public interface OnSelectListener
    {
        boolean onSelect(Hub hub);
    }

    public interface OnEditListener
    {
        boolean onEdit(Hub hub);
    }

    public interface OnDeleteListener
    {
        boolean onDelete(Hub hub);
    }

}
