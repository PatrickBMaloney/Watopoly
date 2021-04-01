package com.example.watopoly.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.watopoly.enums.TileDirection;
import com.example.watopoly.model.Coordinates;
import com.example.watopoly.model.Player;
import com.example.watopoly.model.Tile;
import com.example.watopoly.util.BoardTiles;

import java.util.ArrayList;
import java.util.Map;

public class BoardView extends View {

    private static final float spaceAboveAndBelowBoard = 60;
    private static final float spaceLeftOfBoard = 100;

    // changing numTilesInColumn and numTilesInRow requires changing the tiles array
    private static final int numTilesInColumn = 8;
    private static final float columnTileWidth = 150;
    private static final int numTilesInRow = 9;
    private static final float rowTileHeight = columnTileWidth;

    private static final int totalNumTiles = (numTilesInColumn * 2) + (numTilesInRow * 2) + 4;
    private ArrayList<Tile> tiles = BoardTiles.getTiles();

    // for drawing pieces
    private Map<Tile, ArrayList<Player>> drawingState;
    private boolean drawPieces = false;

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(4);

        float boardHeight = getHeight() - (spaceAboveAndBelowBoard * 2);

        //subtract corner tiles (height of corner tile is width of column tile)
        float cornerTileHeight = columnTileWidth;
        float columnHeight = boardHeight - (cornerTileHeight * 2);

        float tileInColumnHeight = columnHeight/numTilesInColumn;
        float rowTileWidth = tileInColumnHeight;

        float rowWidth = rowTileWidth * numTilesInRow;

        drawColumns(canvas, tileInColumnHeight, cornerTileHeight, rowWidth, paint);
        drawRows(canvas, rowTileWidth, cornerTileHeight, paint);
        drawCornerTiles(canvas, cornerTileHeight, rowWidth, paint);

        if (drawPieces) {
            drawPieces(canvas);
        }
    }

    public void drawColumns(Canvas canvas, float tileInColumnHeight, float cornerTileHeight, float rowWidth, Paint paint) {
        float bottomOffset = getHeight() - spaceAboveAndBelowBoard - cornerTileHeight;
        float leftOffsetForRightColumn = spaceLeftOfBoard + columnTileWidth + rowWidth;

        for (int i = 0; i < numTilesInColumn; i++) {
            // draw left column
            Coordinates leftColumnCoordinates = new Coordinates(
                    spaceLeftOfBoard,
                    bottomOffset - (tileInColumnHeight * (i + 1)),
                    spaceLeftOfBoard + columnTileWidth,
                    bottomOffset - (tileInColumnHeight * (i)));
            drawRectWithCoordinates(canvas, leftColumnCoordinates, paint);
            tiles.get(i+1).setCoordinates(leftColumnCoordinates);
            tiles.get(i+1).drawOn(canvas);

            // draw right column
            Coordinates rightColumnCoordinates = new Coordinates(
                    leftOffsetForRightColumn,
                    bottomOffset - (tileInColumnHeight * (i + 1)),
                    leftOffsetForRightColumn + columnTileWidth,
                    bottomOffset - (tileInColumnHeight * i));
            drawRectWithCoordinates(canvas, rightColumnCoordinates, paint);
            int rightColumnBottomTileIndex = totalNumTiles - numTilesInRow - 2;
            tiles.get(rightColumnBottomTileIndex-i).setCoordinates(rightColumnCoordinates);
            tiles.get(rightColumnBottomTileIndex-i).drawOn(canvas);
        }
    }

    public void drawRows(Canvas canvas, float rowTileWidth, float cornerTileHeight, Paint paint) {
        float leftOffset = spaceLeftOfBoard + cornerTileHeight;
        float bottomOffsetForBottomRow = getHeight() - spaceAboveAndBelowBoard;

        for (int i = 0; i < numTilesInRow; i++) {
            // draw bottom row
            Coordinates bottomRowCoordinates = new Coordinates(
                    leftOffset + (rowTileWidth * i),
                    bottomOffsetForBottomRow,
                    leftOffset + (rowTileWidth * (i + 1)),
                    bottomOffsetForBottomRow - rowTileHeight);
            drawRectWithCoordinates(canvas, bottomRowCoordinates, paint);
            int bottomRowLeftmostTileIndex = totalNumTiles - 1;
            tiles.get(bottomRowLeftmostTileIndex-i).setCoordinates(bottomRowCoordinates);
            tiles.get(bottomRowLeftmostTileIndex-i).drawOn(canvas);

            // draw top row
            Coordinates topRowCoordinates = new Coordinates(
                    leftOffset + (rowTileWidth * i),
                    spaceAboveAndBelowBoard,
                    leftOffset + (rowTileWidth * (i + 1)),
                    spaceAboveAndBelowBoard + rowTileHeight);
            drawRectWithCoordinates(canvas, topRowCoordinates, paint);
            int topRowLeftMostTileIndex = numTilesInColumn + 2;
            tiles.get(topRowLeftMostTileIndex+i).setCoordinates(topRowCoordinates);
            tiles.get(topRowLeftMostTileIndex+i).drawOn(canvas);
        }
    }

    public void drawCornerTiles(Canvas canvas, float cornerTileHeight, float rowWidth, Paint paint) {
        float bottomOffset = getHeight() - spaceAboveAndBelowBoard;
        float leftOffset = spaceLeftOfBoard + rowWidth + columnTileWidth;

        // draw go tile
        Coordinates goCoordinates = new Coordinates(
                spaceLeftOfBoard,
                bottomOffset - cornerTileHeight,
                spaceLeftOfBoard + cornerTileHeight,
                bottomOffset);
        drawRectWithCoordinates(canvas, goCoordinates, paint);
        tiles.get(0).setCoordinates(goCoordinates);
        tiles.get(0).drawOn(canvas);

        // draw jail tile
        Coordinates jailCoordinates = new Coordinates(
                spaceLeftOfBoard,
                spaceAboveAndBelowBoard,
                spaceLeftOfBoard + cornerTileHeight,
                spaceAboveAndBelowBoard + cornerTileHeight);
        drawRectWithCoordinates(canvas, jailCoordinates, paint);
        tiles.get(numTilesInColumn+1).setCoordinates(jailCoordinates);
        tiles.get(numTilesInColumn+1).drawOn(canvas);

        // draw parking tile
        Coordinates parkingCoordinates = new Coordinates(
                leftOffset,
                spaceAboveAndBelowBoard,
                leftOffset + cornerTileHeight,
                spaceAboveAndBelowBoard + cornerTileHeight);
        drawRectWithCoordinates(canvas, parkingCoordinates, paint);
        int parkingIndex = numTilesInColumn + numTilesInRow + 2;
        tiles.get(parkingIndex).setCoordinates(parkingCoordinates);
        tiles.get(parkingIndex).drawOn(canvas);

        // draw goToJail tile
        Coordinates goToJailCoordinates = new Coordinates(
                leftOffset,
                bottomOffset - cornerTileHeight,
                leftOffset + cornerTileHeight,
                bottomOffset );
        drawRectWithCoordinates(canvas, goToJailCoordinates, paint);
        int goToJailIndex = totalNumTiles - numTilesInRow -  1;
        tiles.get(goToJailIndex).setCoordinates(goToJailCoordinates);
        tiles.get(goToJailIndex).drawOn(canvas);
    }


    public void drawRectWithCoordinates(Canvas canvas, Coordinates coordinates, Paint paint) {
        canvas.drawRect(coordinates.getLeft(),
                coordinates.getTop(),
                coordinates.getRight(),
                coordinates.getBottom(),
                paint);
    }

    public void drawPieces(Canvas canvas) {
        Paint p = new Paint();

        for (Tile tile: drawingState.keySet()) {
            ArrayList<Player> players = drawingState.get(tile);
            Coordinates c = tile.getCoordinates();
            if (tile.getTileDirection() == TileDirection.LEFT) {
                float centerWidth = (c.getLeft() + (c.getRight() - 25))/2;
                if (players.size() > 0) {
                    // draw right side
                    Bitmap b = players.get(0).getBitmapIcon();
                    canvas.drawBitmap(b, null, new RectF(centerWidth + 5, c.getTop() + 15, c.getRight() - 25, c.getBottom() - 15) , p);
                }
                if (players.size() == 2) {
                    // draw left side
                    Bitmap b = players.get(1).getBitmapIcon();
                    canvas.drawBitmap(b, null, new RectF(c.getLeft() + 5, c.getTop() + 15, centerWidth - 5, c.getBottom() - 15) , p);
                }
            }
            else if (tile.getTileDirection() == TileDirection.TOP) {
                float centerHeight = (c.getTop() + (c.getBottom() - 25))/2;
                if (players.size() > 0) {
                    // draw bottom side
                    Bitmap b = players.get(0).getBitmapIcon();
                    canvas.drawBitmap(b, null, new RectF(c.getLeft() + 3, centerHeight + 5, c.getRight() - 3, c.getBottom() - 25) , p);
                }
                if (players.size() == 2) {
                    // draw top side
                    Bitmap b = players.get(1).getBitmapIcon();
                    canvas.drawBitmap(b, null, new RectF(c.getLeft() + 3, c.getTop() + 5, c.getRight() - 3,centerHeight - 5) , p);
                }
            }
            else if (tile.getTileDirection() == TileDirection.RIGHT) {
                float centerWidth = (c.getLeft() + 25 + c.getRight())/2;
                if (players.size() > 0) {
                    // draw left side
                    Bitmap b = players.get(0).getBitmapIcon();
                    canvas.drawBitmap(b, null, new RectF(c.getLeft() + 25, c.getTop()  + 15, centerWidth - 5, c.getBottom() - 15) , p);
                }
                if (players.size() == 2) {
                    // draw right side
                    Bitmap b = players.get(1).getBitmapIcon();
                    canvas.drawBitmap(b, null, new RectF(centerWidth + 5, c.getTop()  + 15, c.getLeft() - 5, c.getBottom() - 15) , p);
                }
            }
            else if (tile.getTileDirection() == TileDirection.BOTTOM) {
                float centerHeight = ((c.getTop()-25) + c.getBottom())/2;
                if (players.size() > 0) {
                    Bitmap b = players.get(0).getBitmapIcon();
                    canvas.drawBitmap(b, null, new RectF(c.getLeft() + 10, centerHeight  + 25, c.getRight() - 5, c.getTop() + 5) , p);
                }
                if (players.size() == 2) {
                    Bitmap b = players.get(1).getBitmapIcon();
                    canvas.drawBitmap(b, null, new RectF(c.getLeft() + 10, centerHeight  + 45, c.getRight() - 5, c.getTop() + 5) , p);
                }
            }
            else if (tile.getTileDirection() == TileDirection.CORNER) {
                float centerHeight = (c.getTop() + c.getBottom())/2;
                float centerWidth = (c.getLeft() + c.getRight())/2;

                if (players.size() > 0) {
                    // bottom right
                    Bitmap b = players.get(0).getBitmapIcon();
                    canvas.drawBitmap(b, null, new RectF(centerWidth + 5, centerHeight + 5, c.getRight() - 5, c.getBottom() - 5), p);
                }
                if (players.size() > 1) {
                    // bottom left
                    Bitmap b = players.get(1).getBitmapIcon();
                    canvas.drawBitmap(b, null, new RectF(c.getLeft() + 5, centerHeight + 5, centerWidth - 5, c.getBottom() - 5), p);
                }
                if (players.size() > 2) {
                    // top left
                    Bitmap b = players.get(2).getBitmapIcon();
                    canvas.drawBitmap(b, null, new RectF(c.getLeft() + 5, c.getTop() + 5, centerWidth - 5, centerHeight - 5), p);
                }
                if (players.size() > 3) {
                    // top right
                    Bitmap b = players.get(3).getBitmapIcon();
                    canvas.drawBitmap(b, null, new RectF(centerWidth + 5, c.getTop() + 5, c.getRight() - 5, centerHeight - 5), p);
                }
            }
        }
    }

    public void drawDrawingState(Map<Tile, ArrayList<Player>> drawingState) {
        this.drawPieces = true;
        this.drawingState = drawingState;
        invalidate();
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }
}
