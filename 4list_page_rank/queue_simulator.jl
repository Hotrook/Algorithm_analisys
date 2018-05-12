
function make_move(pos::Int, u::Float64, l::Float64)
    random = rand()

    if pos == 1
        if random < l
            return pos+1
        end
        return pos
    else
        if random < l
            return pos+1
        elseif random >= l && random < u + l
            return pos-1
        else
            return pos
        end
    end
end

function make_move(pos::Int, u::Float64, l::Float64, n::Int)
    random = rand()

    if pos == 1
        if random < l
            return pos+1
        end
        return pos
    elseif pos == n
        if random < u
            return pos-1
        else
            return pos
        end
    else
        if random < l
            return pos+1
        elseif random >= l && random < u + l
            return pos-1
        else
            return pos
        end
    end
end

function simulate_queue(max_time::Int, u::Float64, l::Float64)
    pos = 1
    distribution = fill(0, 10000)

    for time = 1:max_time
        pos = make_move(pos, u, l)
        if pos <= 10000
            distribution[ pos ] += 1
        end
    end

    return map( x -> x/max_time, distribution)
end

function simulate_queue(n::Int, max_time::Int, u::Float64, l::Float64)
    pos = 1
    distribution = fill(0, n)

    for time = 1:max_time
        pos = make_move(pos, u, l, n)
        if pos <= n
            distribution[ pos ] += 1
        end
    end

    return map( x -> x/max_time, distribution)
end

function calculate_distribution(u::Float64, l::Float64)
    x = l/u
    sumx = sum([x ^ i for i = 1:10000])
    result = fill(0.0, 10000)

    result[1] = (1.0 - x)
    for i = 2:10000
        result[ i ] = x^(i-1) * result[ 1 ]
    end

    return result
end

function calculate_distribution(n::Int, u::Float64, l::Float64)
    x = l/u
    sumx = sum([x ^ i for i = 1:n])
    result = fill(0.0, n)

    result[1] = sumx
    for i = 1:n
        result[ i ] = x^(i) / sumx
    end

    return result
end
