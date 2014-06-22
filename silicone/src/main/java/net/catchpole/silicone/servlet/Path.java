package net.catchpole.silicone.servlet;

//   Copyright 2014 catchpole.net
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

import net.catchpole.silicone.lang.Strings;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Path implements Iterable<String> {
    private final List<String> list;
    private final boolean trailingSlash;

    public Path(HttpServletRequest req) {
        this(req.getRequestURI().substring(req.getContextPath().length()));
    }

    public Path(String path) {
        this.list = new ArrayList<String>();

        while (path.contains("..")) {
            path = path.replaceAll("..",".");
        }
        if (path.endsWith("/")) {
            path = path.substring(0, path.length()-1);
            this.trailingSlash = true;
        } else {
            this.trailingSlash = false;
        }
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        String[] array = Strings.tokenize(path, '/');

        for (String s : array) {
            this.list.add(s);
        }
    }

    public Path() {
        this.list = new ArrayList<String>();
        this.trailingSlash = false;
    }

    public Path(Path path) {
        this.list = new ArrayList<String>(path.list);
        this.trailingSlash = path.trailingSlash;
    }

    public Path getTrailingPath(Path base) {
        Path trailingPath = new Path(this);
        Iterator<String> i = trailingPath.list.iterator();
        for (String string : base.list) {
            if (i.hasNext() && string.equals(i.next())) {
                i.remove();
            }
        }
        return trailingPath;
    }

    public Iterator<String> iterator() {
        return this.list.iterator();
    }

    public void add(String string) {
        this.list.add(string);
    }

    public void insert(int index, String string) {
        this.list.add(index, string);
    }

    public String remove(int index) {
        return this.list.remove(index);
    }

    public String get(int index) {
        return this.list.get(index);
    }

    public int getSize() {
        return this.list.size();
    }

    public boolean removeLast() {
        if (this.list.size() > 0) {
            this.list.remove(this.list.size()-1);
            return true;
        }
        return false;
    }

    public int getDepth() {
        return this.list.size() == 0 ? 0 : this.list.size() - (this.trailingSlash ? 0 : 1);
    }

    public boolean hasTrailingSlash() {
        return this.trailingSlash;
    }

    @Override
    public int hashCode() {
        return this.list.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return this.list.equals(((Path)o).list);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : this.list) {
            stringBuilder.append("/");
            stringBuilder.append(s);
        }
        if (this.trailingSlash) {
            stringBuilder.append("/");
        }
        return stringBuilder.toString();
    }
}
