package verkstad.org.in.valentineapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by anu on 1/20/2016.
 */
public class RecyclerViewAdapterlb extends RecyclerView.Adapter<RecyclerViewAdapterlb.ViewHolderlb> {
    LayoutInflater inflater;
    ArrayList<String> names_leader_board,counts_red_leader_board,counts_yellow_leader_board;
    ArrayList<Integer> ranks_leader_board;
    public RecyclerViewAdapterlb(Context context,ArrayList<Integer> ranks_leader_board,ArrayList<String> names_leader_board,
                                 ArrayList<String> counts_red_leader_board,ArrayList<String> counts_yellow_leader_board){
        inflater= LayoutInflater.from(context);
        this.ranks_leader_board=ranks_leader_board;
        this.counts_red_leader_board=counts_red_leader_board;
        this.names_leader_board=names_leader_board;
        this.counts_yellow_leader_board=counts_yellow_leader_board;

    }

    @Override
    public ViewHolderlb onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.leader_board_row,parent,false);
        ViewHolderlb holderlb = new ViewHolderlb(view);
        return holderlb;
    }

    @Override
    public void onBindViewHolder(ViewHolderlb holder, int position) {
        String name_leader_board=names_leader_board.get(position);
        int rank_leader_board=ranks_leader_board.get(position);
        String count_red_leader_board=counts_red_leader_board.get(position);
        String count_yellow_leader_board = counts_yellow_leader_board.get(position);
        holder.rank_leader_board.setText(""+rank_leader_board);
        holder.name_leader_board.setText(name_leader_board);
        holder.count_red_leader_board.setText(count_red_leader_board);
        holder.count_yellow_leader_board.setText(count_yellow_leader_board);
    }

    @Override
    public int getItemCount() {
        return names_leader_board.size();
    }

    class ViewHolderlb extends RecyclerView.ViewHolder {
        TextView rank_leader_board,name_leader_board,count_red_leader_board,count_yellow_leader_board;
        public ViewHolderlb(View itemView) {
            super(itemView);
            rank_leader_board= (TextView) itemView.findViewById(R.id.rank_leader_board);
            name_leader_board= (TextView) itemView.findViewById(R.id.name_leader_board);
            count_red_leader_board= (TextView) itemView.findViewById(R.id.count_red_leader_board);
            count_yellow_leader_board= (TextView) itemView.findViewById(R.id.count_yellow_leader_board);
        }
    }
}
