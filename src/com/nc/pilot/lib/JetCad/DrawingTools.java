package com.nc.pilot.lib.JetCad;

import com.nc.pilot.lib.MDIConsole.MDICommand;

import java.util.ArrayList;

public class DrawingTools {
    private ArrayList<DrawingToolStruct> ToolStack = new ArrayList();

    private void AddTool(String name, String hotkey, Runnable action)
    {
        DrawingToolStruct tool = new DrawingToolStruct();
        tool.name = name;
        tool.hotkey = hotkey;
        tool.action = action;
        ToolStack.add(tool);
    }
    private void SelectAll()
    {

    }
    private void SaveDrawing()
    {

    }
    private void CopySelected()
    {

    }
    private void PasteSelected()
    {

    }
    private void Undo()
    {

    }
    private void DeleteSelected()
    {

    }
    private void DrawLine()
    {

    }
    private void DrawRectangle()
    {

    }
    private void DrawCircle()
    {

    }
    private void Trim()
    {

    }
    private void Fillet()
    {

    }
    private void Offset()
    {

    }
    private void Move()
    {

    }
    private void ToggleConstructionStyle()
    {

    }
    private void SearchToolBox()
    {

    }
    private void ParallelLine()
    {

    }
    private void Escape()
    {

    }
    private void ChainSelect()
    {

    }
    private void Rotate()
    {

    }
    private void CircularPattern()
    {

    }
    private void Scale()
    {

    }
    private void Mirror()
    {

    }
    private void Color()
    {

    }
    private void Layer()
    {

    }
    private void Text()
    {

    }
    private void DrawTextAlongPath()
    {

    }
    private void CleanDrawing()
    {

    }
    private void Slot()
    {

    }
    private void Chamfer()
    {

    }
    private void Fit()
    {

    }
    private void InvertDelete()
    {

    }
    private void RemoveDuplicates()
    {

    }
    private void LatheMode()
    {

    }
    public DrawingTools()
    {
        AddTool("select_all", "ctrl-a", new Runnable() {
            @Override
            public void run() {
                SelectAll();
            }
        });
        AddTool("save_drawing", "ctrl-s", new Runnable() {
            @Override
            public void run() {
                SaveDrawing();
            }
        });
        AddTool("copy", "ctrl-c", new Runnable() {
            @Override
            public void run() {
                CopySelected();
            }
        });
        AddTool("paste", "ctrl-v", new Runnable() {
            @Override
            public void run() {
                PasteSelected();
            }
        });
        AddTool("undo", "ctrl-z", new Runnable() {
            @Override
            public void run() {
                Undo();
            }
        });
        AddTool("delete", "ctrl-d", new Runnable() {
            @Override
            public void run() {
                DeleteSelected();
            }
        });
        AddTool("line", "l", new Runnable() {
            @Override
            public void run() {
                DrawLine();
            }
        });
        AddTool("rectangle", "r", new Runnable() {
            @Override
            public void run() {
                DrawRectangle();
            }
        });
        AddTool("circle", "c", new Runnable() {
            @Override
            public void run() {
                DrawCircle();
            }
        });
        AddTool("trim", "t", new Runnable() {
            @Override
            public void run() {
                Trim();
            }
        });
        AddTool("fillet", "f", new Runnable() {
            @Override
            public void run() {
                Fillet();
            }
        });
        AddTool("offset", "o", new Runnable() {
            @Override
            public void run() {
                Offset();
            }
        });
        AddTool("move", "m", new Runnable() {
            @Override
            public void run() {
                Move();
            }
        });
        AddTool("", "x", new Runnable() {
            @Override
            public void run() {
                ToggleConstructionStyle();
            }
        });
        AddTool("", "s", new Runnable() {
            @Override
            public void run() {
                SearchToolBox();
            }
        });
        AddTool("parallel_line", "p", new Runnable() {
            @Override
            public void run() {
                ParallelLine();
            }
        });
        AddTool("", "Escape", new Runnable() {
            @Override
            public void run() {
                Escape();
            }
        });
        AddTool("chain_select", "Space", new Runnable() {
            @Override
            public void run() {
                ChainSelect();
            }
        });
        AddTool("rotate", "", new Runnable() {
            @Override
            public void run() {
                Rotate();
            }
        });
        AddTool("circular_pattern", "", new Runnable() {
            @Override
            public void run() {
                CircularPattern();
            }
        });
        AddTool("scale", "", new Runnable() {
            @Override
            public void run() {
                Scale();
            }
        });
        AddTool("mirror", "", new Runnable() {
            @Override
            public void run() {
                Mirror();
            }
        });
        AddTool("color", "", new Runnable() {
            @Override
            public void run() {
                Color();
            }
        });
        AddTool("layer", "", new Runnable() {
            @Override
            public void run() {
                Layer();
            }
        });
        AddTool("text", "", new Runnable() {
            @Override
            public void run() {
                Text();
            }
        });
        AddTool("path_text", "", new Runnable() {
            @Override
            public void run() {
                DrawTextAlongPath();
            }
        });
        AddTool("clean", "", new Runnable() {
            @Override
            public void run() {
                CleanDrawing();
            }
        });
        AddTool("slot", "", new Runnable() {
            @Override
            public void run() {
                Slot();
            }
        });
        AddTool("chamfer", "", new Runnable() {
            @Override
            public void run() {
                Chamfer();
            }
        });
        AddTool("fit", "", new Runnable() {
            @Override
            public void run() {
                Fit();
            }
        });
        AddTool("invert_delete", "", new Runnable() {
            @Override
            public void run() {
                InvertDelete();
            }
        });
        AddTool("remove_duplicates", "", new Runnable() {
            @Override
            public void run() {
                RemoveDuplicates();
            }
        });
        AddTool("lathe_mode", "", new Runnable() {
            @Override
            public void run() {
                LatheMode();
            }
        });
    }
}
