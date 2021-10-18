package com.yoctopuce.yoctopucetoolbox.misc;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.hub.Hub;


public class HubViewHolder extends RecyclerView.ViewHolder
{
    private final ImageView _iconImageView;
    private final TextView _urlTextView;
    private final TextView _firstTextView;
    private final ImageView _editImageView;
    private final ProgressBar _progressBar;
    private Hub _hub;

    public HubViewHolder(View itemView)
    {
        super(itemView);
        _firstTextView = itemView.findViewById(R.id.hub_line1);
        _urlTextView = itemView.findViewById(R.id.hub_line2);
        _editImageView = itemView.findViewById(R.id.edit);
        _iconImageView = itemView.findViewById(R.id.icon);
        _progressBar = itemView.findViewById(R.id.progressBar);

    }

    public void bindHub(final Hub hub, final OnSelectListener selectListener, final OnEditListener editListener, final OnDeleteListener deleteListener)
    {
        _hub = hub;


        if (hub.isUSB()) {
            _iconImageView.setImageResource(R.drawable.outline_usb_black_48);
        } else {
            if (hub.isOnline()) {
                int icon2d_res = -1;
                switch (hub.getBaseSerial()) {
                    //todo download Icon2d if device is unknown
                    case "YHUBETH1":
                        icon2d_res = R.drawable.icon2d_yhubeth1;
                        break;
                    case "YHUBGSM1":
                        icon2d_res = R.drawable.icon2d_yhubgsm1;
                        break;
                    case "YHUBGSM3":
                    case "YHUBGSM4":
                        icon2d_res = R.drawable.icon2d_yhubgsm4;
                        break;
                    case "YHUBGSM5":
                        icon2d_res = R.drawable.icon2d_yhubgsm5;
                        break;
                    case "YHUBWLN1":
                        icon2d_res = R.drawable.icon2d_yhubwln1;
                        break;
                    case "YHUBWLN2":
                        icon2d_res = R.drawable.icon2d_yhubwln2;
                        break;
                    case "YHUBWLN3":
                        icon2d_res = R.drawable.icon2d_yhubwln3;
                        break;
                    case "YHUBWLN4":
                        icon2d_res = R.drawable.icon2d_yhubwln4;
                        break;
                    default:
                        icon2d_res = R.drawable.yocto_circle_40_dp;
                        break;
                }
                if (hub.isBeacon()) {
                    _iconImageView.setImageResource(R.drawable.yocto_circle_blue_40_dp);
                } else {
                    _iconImageView.setImageResource(icon2d_res);
                }
            } else {
                _iconImageView.setImageResource(R.drawable.yocto_circle_40_dp);
            }
        }

        //String mystring = getResources().getString(R.string.mystring);
        _firstTextView.setText(hub.getDescription(_firstTextView.getContext()));
        _urlTextView.setText(_hub.getUrl(false));
        _progressBar.setVisibility(hub.isRefreshing() ? ProgressBar.VISIBLE : ProgressBar.INVISIBLE);
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
