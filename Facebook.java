/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facebook;

import Account.Account;
import Collection.Collection;
import Collection.CollectionManager;
import Collection.Group;
import Collection.Page;
import Post.Responce.ImgPost;
import Post.Responce.Post;
import Post.Responce.PostManager;
import Post.Responce.ResponceManager;
import Post.Responce.TextPost;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MHA
 */
public class Facebook {

    private List<Account> users;
    private List<Collection> collection;

    private static Facebook F;

    private Facebook() {

        users = null;
        users = new ArrayList();
        collection = new ArrayList();

    }

    public static Facebook getInstance() {
        if (F == null) {
            F = new Facebook();
        }
        return F;
    }

    public boolean addAccount(Account N) {
        if (isAnAccount(N.getEmail()) != -1) {
            return false;
        }
        users.add(N);
        return true;
    }
    
    public boolean signIn(String e,String p) {
        int A = isAnAccount(e);
        if(A!=-1){         
            if(users.get(A).getPassword() == null ? p == null : users.get(A).getPassword().equals(p)){
                return true;
            }      
        }      
        return false;
    }

    public int isAnAccount(String N) {

        Account E;

        for (int i = 0; i < users.size(); i++) {

            E = users.get(i);

            if (E.getEmail() == null ? N == null : E.getEmail().equals(N)) {
                return i;
            }

        }

        return -1;
    }

    public boolean removeCollection(String name) {

        CollectionManager m = new CollectionManager(collection);

        if (m.removeCollection(name)) {
            return true;
        }

        return false;
    }

    public boolean addFriend(String user, String newf) {
        Account u;
        Account nf;

        u = users.get(isAnAccount(user));
        nf = users.get(isAnAccount(newf));
        if (u != null && nf != null) {
            u.addFriend(newf, nf);
            nf.addFriend(user, u);
            return true;
        }
        return false;
    }
    
    
    public List<Account> getFriend(String user) {
        Account u;

        u = users.get(isAnAccount(user));
        if (u != null) {
                
            return u.getFriends();
        }
        return null;
        
    }

    public boolean removeFriend(String user, String newf) {
        Account u;
        Account nf;

        u = users.get(isAnAccount(user));
        nf = users.get(isAnAccount(newf));
        if (u != null && nf != null) {
            u.removeFriend(newf, nf);
            nf.removeFriend(user, u);
            return true;
        }
        return false;
    }

    public boolean addFriend(String user, String newf, String message) {
        Account u;
        Account nf;

        u = users.get(isAnAccount(user));
        nf = users.get(isAnAccount(newf));
        if (u != null && nf != null) {
            u.addFriend(newf, nf);
            nf.addFriend(user, u);
            return true;
        }
        return false;
    }

    public void addMessage(String user, String newf, String message, boolean newmes) {
        Account u;
        Account nf;

        u = users.get(isAnAccount(user));
        nf = users.get(isAnAccount(newf));
        if (u != null && nf != null) {
            if (newmes) {
                u.addMessage(u, nf, message, true);
                nf.addMessage(u, nf, message, false);
            } else {
                u.addMessage(u, nf, message, false);
                nf.addMessage(u, nf, message, false);
            }
        }

    }

    public boolean addFriendRequest(String user, String newf) {
        Account u;
        Account nf;

        u = users.get(isAnAccount(user));
        nf = users.get(isAnAccount(newf));
        if (u != null && nf != null) {
            u.addFriendRequest(newf, nf, false);
            nf.addFriendRequest(user, u, true);
            return true;
        }
        return false;
    }

    public boolean removeFriendRequest(String user, String newf) {
        Account u;
        Account nf;

        u = users.get(isAnAccount(user));
        nf = users.get(isAnAccount(newf));
        if (u != null && nf != null) {
            u.RemoveFriendRequest(newf, nf);
            nf.RemoveFriendRequest(user, u);
            return true;
        }
        return false;
    }

    public boolean answerFriendRequest(String user, String newf, boolean accept) {
        Account u;
        Account nf;

        u = users.get(isAnAccount(user));
        nf = users.get(isAnAccount(newf));
        if (u != null && nf != null) {
            u.RemoveFriendRequest(newf, nf);
            nf.RemoveFriendRequest(user, u);
            if (accept) {
                u.addFriend(newf, nf);
                nf.addFriend(user, u);
            }
            return true;
        }
        return false;
    }

    public boolean addGroup(String nam, String des) {

        CollectionManager m = new CollectionManager(collection);

        if (m.isACollection(nam)) {
            return false;
        }
        m.addCollection(nam, des, 'G');
        collection.add(new Page(nam, des));

        return true;
    }

    public boolean addPage(String nam, String des) {
        CollectionManager m = new CollectionManager(collection);

        if (m.isACollection(nam)) {
            return false;
        }
        m.addCollection(nam, des, 'P');
        collection.add(new Page(nam, des));

        return true;

    }

    public boolean addAdmin(String gName, String uName, boolean newC) {

        for (int i = 0; i < collection.size(); i++) {
            if (gName == null ? collection.get(i).getName() == null : gName.equals(collection.get(i).getName())) {

                if (collection.get(i).isAdmin(uName)) {
                    return false;
                }

                int A = isAnAccount(uName);

                if (A != -1) {
                    if (newC) {
                        CollectionManager m = new CollectionManager(collection);
                        m.addCollectionAdmin(gName, uName);
                    }
                    collection.get(i).addAdmin(users.get(A));
                    return true;
                }
            }
        }

        return false;
    }

    public boolean addMemeber(String gName, String uName, boolean newC) {

        for (int i = 0; i < collection.size(); i++) {
            if (gName == null ? collection.get(i).getName() == null : gName.equals(collection.get(i).getName())) {

                if (collection.get(i).isMember(uName)) {
                    return false;
                }

                if (collection.get(i).isAdmin(uName)) {
                    return false;
                }

                int A = isAnAccount(uName);

                if (A != -1) {
                    if (newC) {
                        CollectionManager m = new CollectionManager(collection);
                        m.addCollectionMember(gName, uName);
                    }
                    collection.get(i).addMember(users.get(A));
                    return true;
                }
            }
        }

        return false;

    }

    public boolean removeAdmin(String gName, String uName) {

        for (int i = 0; i < collection.size(); i++) {
            if (gName == null ? collection.get(i).getName() == null : gName.equals(collection.get(i).getName())) {

                if (!collection.get(i).isAdmin(uName)) {
                    return false;
                }

                int A = isAnAccount(uName);

                if (A != -1) {

                    CollectionManager m = new CollectionManager(collection);

                    if (collection.get(i).removeAdmin(users.get(A))) {
                        m.removeCollectionAdmin(gName, uName);
                        return true;
                    }

                    return false;
                }
            }
        }

        return false;
    }

    public boolean removeMemeber(String gName, String uName) {

        for (int i = 0; i < collection.size(); i++) {
            if (gName == null ? collection.get(i).getName() == null : gName.equals(collection.get(i).getName())) {

                if (!collection.get(i).isMember(uName)) {
                    return false;
                }

                int A = isAnAccount(uName);

                if (A != -1) {

                    CollectionManager m = new CollectionManager(collection);
                    collection.get(i).removeMember(users.get(A));
                    m.removeCollectionMember(gName, uName);

                    return true;
                }
            }
        }

        return false;

    }

    public Post addUserPost(String use, String tex, String typ, String id) {
        int A = isAnAccount(use);
        if (A != -1) {
            if ("I".equals(typ)) {
                if (!"".equals(id)) {
                    return users.get(A).addPost(new ImgPost(use, id, tex));
                } else {
                    Post as = users.get(A).addPost(new ImgPost(use, id, tex));
                    PostManager pm = new PostManager(users.get(A).getPost());
                    as.setId("" + pm.addPost(use, tex, typ, ""));
                    return as;
                }
            }
            if ("T".equals(typ)) {
                if (!"".equals(id)) {
                    return users.get(A).addPost(new TextPost(use, id, tex));
                } else {
                    Post as = users.get(A).addPost(new TextPost(use, id, tex));
                    PostManager pm = new PostManager(users.get(A).getPost());
                    int k = pm.addPost(use, tex, typ, "");
                    as.setId("" + k);
                    return as;
                }
            }
        }
        return null;
    }

    public boolean removeUserPost(String use, String id) {
        int A = isAnAccount(use);

        PostManager pm = new PostManager(users.get(A).getPost());
        if (pm.isPost(id)) {
            users.get(A).removePost(id);
            pm.removePost(id);
            return true;
        }

        return false;
    }

    public boolean removeCollectionPost(String use, String id, String cc) {
        int A = isAnAccount(use);
        CollectionManager cm = new CollectionManager(collection);

        Collection col = cm.getCollection(cc);

        PostManager pm = new PostManager(col.getPost());
        if (pm.isPost(id)) {
            col.removePost(id);
            pm.removePost(id);
            return true;
        }

        return false;
    }

    public Post addCollectionPost(String use, String tex, String typ, String id, String cc) {
        int A = isAnAccount(use);

        CollectionManager cm = new CollectionManager(collection);

        Collection col = cm.getCollection(cc);

        if (A != -1) {
            if (col != null) {
                if ("I".equals(typ)) {
                    if (!"".equals(id)) {
                        return col.addPost(new ImgPost(use, id, tex));
                    } else {
                        Post as = col.addPost(new ImgPost(use, id, tex));
                        PostManager pm = new PostManager(col.getPost());
                        as.setId("" + pm.addPost(use, tex, typ, cc));
                        return as;
                    }
                }
                if ("T".equals(typ)) {
                    if (col != null) {
                        if (!"".equals(id)) {
                            return col.addPost(new TextPost(use, id, tex));
                        } else {
                            Post as = col.addPost(new TextPost(use, id, tex));
                            PostManager pm = new PostManager(col.getPost());
                            as.setId("" + pm.addPost(use, tex, typ, cc));
                            return as;
                        }
                    }
                }
            }
        }
        return null;
    }

    public void addComment(Post p, String em, String tx, boolean isNew) {

        int A = isAnAccount(em);

        if (A != -1) {

            p.AddComment(em, tx);

            if (isNew) {
                ResponceManager rm = new ResponceManager(p.getRes());
                rm.addResponce(p.getId(), "C", em, tx);
            }

        }

    }

    public void removeComment(Post p, String em, String tx) {

        int A = isAnAccount(em);

        if (A != -1) {

            p.RemoveComment(em, tx);

            ResponceManager rm = new ResponceManager(p.getRes());
            rm.removeResponce(p.getId(), "C", em, tx);
            
        }

    }

    public void changeLike(Post p, String em, boolean isNew) {

        int A = isAnAccount(em);

        if (A != -1) {

            boolean b = p.setLike(em);

            if (isNew) {
                ResponceManager rm = new ResponceManager(p.getRes());
                if(b)
                    rm.addResponce(p.getId(), "L", em, "");
                else
                    rm.removeResponce(p.getId(), "L", em, "");
            }

        }

    }

}
