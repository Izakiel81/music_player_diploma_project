package izakiel81.develop.music_player_diploma;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {

    private ArrayList<String> list;
    private Context mContext;

    public MusicAdapter(ArrayList<String> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    // Створення нового елементу RecyclerView
    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Створення View для елемента списку з використанням item_layout.xml
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);

        // Повернення нового об'єкту MusicViewHolder з цим View
        return new MusicViewHolder(view);
    }

    // Прив'язка даних до елемента RecyclerView
    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder,
                                 int position) {
        // Отримання шляху до музичного файлу за позицією у списку
        String path = list.get(position);
        // Отримання назви файлу з його шляху
        final String title = path.substring(path.lastIndexOf("/") + 1);
        // Встановлення назви файлу у TextView
        holder.textViewFileName.setText(title);

        // Встановлення обробника натискання на елемент RecyclerView
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Створення інтенції для відображення MusicActivity
                Intent intent = new Intent(mContext, MusicActivity.class);

                // Додавання додаткових даних у інтенцію
                intent.putExtra("title", title);
                intent.putExtra("path", path);
                intent.putExtra("position", position);
                intent.putExtra("list", list);

                // Запуск активності MusicActivity
                mContext.startActivity(intent);
            }
        });
    }

    // Повернення кількості елементів у списку
    @Override
    public int getItemCount() {
        return list.size();
    }

    // Внутрішній клас для утримання елементів View для кожного елемента RecyclerView
    public static class MusicViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewFileName;
        private final CardView cardView;

        // Конструктор, який приймає View як параметр
        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);

            // Ініціалізація елементів View
            textViewFileName = itemView.findViewById(R.id.textViewFileName);
            cardView = itemView.findViewById(R.id.cardViewItem);
        }
    }
}
