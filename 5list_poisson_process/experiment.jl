import Base.isless

@enum Client_type start stop
type Client
    in_time::Float64
    out_time::Float64
    que::Int
    op_type::Client_type
end

function isless(first::Client, second::Client)
    first.out_time < second.out_time
end

function poiss_dist(lambda::Float64)
    -log(rand())/lambda
end

function carry_experiment(n::Int, t::Int, lambda::Float64, get_random::Function)
    ques = Vector{Queue}(n)
    pq = PriorityQueue()
    client_num = 0

    current_time = 0.0
    sum_times = 0.0

    for i = 1:n
        ques[ i ] = Queue(Client)
        in_time = poiss_dist(lambda)
        enqueue!(pq, Client(in_time, in_time, i, start), in_time)
    end

    while current_time < t

        current_task = dequeue!(pq)
        current_time = current_task.out_time

        if current_task.op_type == start
            q = current_task.que
            next_int_time = poiss_dist(lambda) + current_time
            enqueue!(pq, Client(next_int_time, next_int_time, q, start), next_int_time)

            if isempty(ques[q])
                finish_time = get_random() + current_time
                current_task.op_type = stop
                current_task.out_time = finish_time
                enqueue!(pq, current_task, finish_time)
                enqueue!(ques[q], current_task)
            else
                enqueue!(ques[q], current_task)
            end
        elseif current_task.op_type == stop
            q = current_task.que
            sum_times += (current_task.out_time - current_task.in_time)
            client_num += 1
            dequeue!(ques[q])

            if !isempty(ques[q])
                current_task = front(ques[q])
                current_task.op_type = stop
                current_task.out_time = get_random() + current_time
                enqueue!(pq, current_task, current_task.out_time)
            end
        end
    end

    sum_times / client_num
end
