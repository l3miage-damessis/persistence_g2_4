package edu.uga.miage.m1.g2_4.polygons.metier.shapes;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;

import edu.uga.miage.m1.g2_4.polygons.metier.persistence.Visitor;



/**
 * This inner class implements the triangle <tt>SimpleShape</tt> service.
 * It simply provides a <tt>draw()</tt> that paints a triangle.
 *
 * @author <a href=
 *         "mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class Triangle extends SimpleShape{
    int x;
    int y;
    boolean selected;

    Triangle(int x, int y) {
        this.x = x - 25;
        this.y = y - 25;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gradient = new GradientPaint(x, y, Color.GREEN, x + Float.valueOf(50), y, Color.WHITE);
        g2d.setPaint(gradient);
        int[] xcoords = { x + 25, x, x + 50 };
        int[] ycoords = { y, y + 50, y + 50 };
        GeneralPath polygon = new GeneralPath(java.awt.geom.Path2D.WIND_EVEN_ODD, xcoords.length);
        polygon.moveTo(x + Float.valueOf(25), y);
        for (int i = 0; i < xcoords.length; i++) {
            polygon.lineTo(xcoords[i], ycoords[i]);
        }
        polygon.closePath();
        g2d.fill(polygon);
        BasicStroke wideStroke = new BasicStroke(2.0f);
        g2d.setColor(Color.black);
        g2d.setStroke(wideStroke);
        g2d.draw(polygon);

    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean doesXIsInShapeBounds(int x) {
        return isCoordinateInShapeBounds(getX(),x);
    }

    @Override
    public boolean doesYIsInShapeBounds(int y) {
        return isCoordinateInShapeBounds(getY(),y);
    }

    @Override
    public boolean isCoordinateInShapeBounds(int shapeCoordinate, int targetCoordinate){
        int shapeBoundary = shapeCoordinate + 50;
        return (shapeBoundary > targetCoordinate && shapeCoordinate < targetCoordinate);
    }

    @Override
    public boolean doesCoordinatesAreInShapeBounds(int x, int y) {
        return (doesXIsInShapeBounds(x) && doesYIsInShapeBounds(y));
    }

    @Override
    public void moveTo(int targetPositionX, int targetPositionY) {
        int dx = targetPositionX - this.getX() - 25;
        int dy = targetPositionY - this.getY() - 25;
        this.x += dx;
        this.y += dy;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
