package com.tutorat.assocoma.QCM;

/**
 * Created by Sidd8 on 12/10/2014.
 */
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter
{
    private Activity context;
    private Map<String, List<String>> matieres;
    private Map<String, List<String>> idListSousMatieres;
    private List<String> sousMatieres;
    public boolean isClicked;

    public ExpandableListAdapter(Activity context, List<String> laptops, Map<String, List<String>> laptopCollections,Map<String, List<String>> idListSousMatieres)
    {
        this.context = context;
        this.matieres = laptopCollections;
        this.idListSousMatieres = idListSousMatieres;
        this.sousMatieres = laptops;
    }

    public Object getChild(int groupPosition, int childPosition)
    {
        if(!isClicked)
            return matieres.get(sousMatieres.get(groupPosition)).get(childPosition);
        else
            return idListSousMatieres.get(sousMatieres.get(groupPosition)).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }


    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        final String laptop = (String) getChild(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.child_item, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.tv_sous_matiere);

        item.setText(laptop);
        return convertView;
    }

    public int getChildrenCount(int groupPosition)
    {
        return matieres.get(sousMatieres.get(groupPosition)).size();
    }

    public Object getGroup(int groupPosition)
    {
        return sousMatieres.get(groupPosition);
    }

    public int getGroupCount()
    {
        return sousMatieres.size();
    }

    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        String laptopName = (String) getGroup(groupPosition);
        if (convertView == null)
        {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item,null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.tv_matiere);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(laptopName);
        return convertView;
    }

    public boolean hasStableIds()
    {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }
}