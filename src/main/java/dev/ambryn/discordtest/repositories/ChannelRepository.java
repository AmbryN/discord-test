package dev.ambryn.discordtest.repositories;

import dev.ambryn.discordtest.beans.Channel;
import dev.ambryn.discordtest.beans.Message;
import dev.ambryn.discordtest.beans.User;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Dependent
public class ChannelRepository {
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    @Transactional
    public Optional<Channel> createChannel(Channel channel) {
        try {
            em.persist(channel);
            em.flush();
            return Optional.of(channel);
        } catch (PersistenceException e) {
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<Channel> getChannel(Long id) {
        try {
            Channel channel = (Channel) em.createQuery("SELECT c FROM Channel c WHERE c.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.of(channel);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public void addMessage(Long id, Message message) {
        getChannel(id).ifPresent(channel -> channel.addMessage(message));
    }

    @Transactional
    public void updateChannel(Channel channel) {
        em.merge(channel);
    }
}
