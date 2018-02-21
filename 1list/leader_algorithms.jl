function leader_firstscenario(n::Integer)
    probability = 1 / n
    leader_found = false
    steps = 0
    transmitted = 0

    while transmitted != 1
        transmitted = 0
        for i = 1:n
            if rand() < probability
                transmitted += 1
            end
        end
        steps += 1
    end

    return steps
end
