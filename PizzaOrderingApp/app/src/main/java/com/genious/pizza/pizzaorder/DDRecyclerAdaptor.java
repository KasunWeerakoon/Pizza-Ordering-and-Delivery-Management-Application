package com.genious.pizza.pizzaorder;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DDRecyclerAdaptor extends RecyclerView.Adapter<DDRecyclerAdaptor.ViewHolder> {
    ArrayList<String> dataSource=new ArrayList<String>();
    ArrayList<String> pSmall=new ArrayList<String>();
    ArrayList<String> pMedium=new ArrayList<String>();
    ArrayList<String> pLarge=new ArrayList<String>();
    ArrayList<String> pItem =new ArrayList<String>();
    Context contextA;
    int[] pizzaImage;

    public DDRecyclerAdaptor(int[] Image,List<ParseObject> scoreList, String cases){
        pizzaImage=Image;


        switch (cases) {
            case "main_menu": {

                for (int i = 0; i < scoreList.size(); i++) {

                    ParseObject object = scoreList.get(i);
                    String name = ((ParseObject) object).get("Name").toString();
                    String item = ((ParseObject) object).get("Item_ID").toString();
                    String small = ((ParseObject) object).get("Personal_pane").toString();
                    String medium = ((ParseObject) object).get("Medium").toString();
                    String large = ((ParseObject) object).get("Large").toString();
                    dataSource.add(i, name);
                    pSmall.add(i,small);
                    pMedium.add(i,medium);
                    pLarge.add(i,large);
                    pItem.add(i, item);
                    Log.v("Data", name+item);

                }
                ;
            }

        }
    }
    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_list_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textView.setText(dataSource.get(position));
        holder.sPrice.setText(pSmall.get(position));
        holder.mPrice.setText(pMedium.get(position));
        holder.lPrice.setText(pLarge.get(position));
        holder.imageView.setBackgroundResource(pizzaImage[position]);

        holder.detailText.setText("\""+dataSource.get(position)+"\""+"\""+pSmall.get(position)+"\"\""+pMedium.get(position)+"\"\""+pLarge.get(position)+"\"\""+pizzaImage[position]+"\"\""+pItem.get(position)+"\"");
        String line="kasun \"a111\" pradeep ";
        String name="kasun \"pradeep\" weerakoon \"bandara\"";
        Pattern p = Pattern.compile("\"([^\"]*)\"");
        Matcher m = p.matcher(name);
        while (m.find()) {
            System.out.println(m.group(1));
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context=v.getContext();
                Intent intent=new Intent(context,AddExtra.class);
                CharSequence details=holder.detailText.getText();
                Log.v("text",""+details);
                intent.putExtra("Details", "asdasdsdads" + details);
                intent.putExtra("Select","add_extra");
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        protected TextView textView;
        protected TextView sPrice;
        protected TextView mPrice;
        protected TextView lPrice;
        protected CardView cardView;
        protected ImageView imageView;
        protected TextView detailText;

        public ViewHolder(View itemView) {
            super(itemView);
            textView =  (TextView) itemView.findViewById(R.id.pNameTxt);
            sPrice=(TextView)itemView.findViewById(R.id.sPrice);
            mPrice=(TextView)itemView.findViewById(R.id.mPrice);
            lPrice=(TextView)itemView.findViewById(R.id.lPrice);
            cardView=(CardView)itemView.findViewById(R.id.card_view);
            imageView=(ImageView)itemView.findViewById(R.id.imageView);
            detailText=(TextView)itemView.findViewById(R.id.detailText);
        }


    }
}
