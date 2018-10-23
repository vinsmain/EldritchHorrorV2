package ru.mgusev.eldritchhorror.interfaces;

public interface OnItemClicked {
    void onItemClick(int position);
    void onItemLongClick(int position);
    void onEditClick(int position);
    void onDeleteClick(int position);
}
