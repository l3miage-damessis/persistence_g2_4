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
package edu.uga.miage.m1.g2_4.polygons.metier.shapes;

import java.awt.Graphics2D;

import edu.uga.miage.m1.g2_4.polygons.metier.persistence.Visitor;
import edu.uga.singleshape.CubePanel;

public class Cube extends SimpleShape {
    int x;
    int y;
    boolean selected;

    Cube(int x, int y) {
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
            CubePanel c = new CubePanel(100, x, y);
            c.paintComponent(g2d);
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
